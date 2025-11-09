package com.tns.seocrawler.service;

import com.tns.seocrawler.domain.CrawlLog;
import com.tns.seocrawler.domain.Post;
import com.tns.seocrawler.domain.Source;
import com.tns.seocrawler.domain.enumeration.CrawlStatus;
import com.tns.seocrawler.domain.enumeration.PostStatus;
import com.tns.seocrawler.repository.CrawlLogRepository;
import com.tns.seocrawler.repository.PostRepository;
import com.tns.seocrawler.repository.SourceRepository;
import java.time.Instant;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class CrawlerService {

    private final Logger log = LoggerFactory.getLogger(CrawlerService.class);

    private final SourceRepository sourceRepository;
    private final PostRepository postRepository;
    private final CrawlLogRepository crawlLogRepository;

    public CrawlerService(SourceRepository sourceRepository, PostRepository postRepository, CrawlLogRepository crawlLogRepository) {
        this.sourceRepository = sourceRepository;
        this.postRepository = postRepository;
        this.crawlLogRepository = crawlLogRepository;
    }

    /**
     * JOB TỰ ĐỘNG
     * - @Scheduled: Spring sẽ tự gọi hàm này sau mỗi khoảng fixedDelay
     * - fixedDelayString đọc từ cấu hình: app.crawler.fixed-delay-ms
     *   (nếu không cấu hình thì mặc định 300000ms = 5 phút)
     *
     * Mục đích: định kỳ quét toàn bộ nguồn đang active.
     */
    @Scheduled(fixedDelayString = "${app.crawler.fixed-delay-ms:600000}")
    public void crawlAllSourcesScheduled() {
        log.debug("Scheduled crawler triggered");
        crawlAllSources();
    }

    /**
     * HÀM CRAWL CHUNG
     * - Dùng cho cả scheduler phía trên
     * - Và cho API manual trigger (admin bấm Run now)
     */
    public void crawlAllSources() {
        // Lấy tất cả Source đang bật + thuộc Tenant đang ACTIVE
        List<Source> sources = sourceRepository.findAllActiveForActiveTenants();
        log.info("Found {} active sources to crawl", sources.size());

        for (Source source : sources) {
            try {
                crawlSource(source);
            } catch (Exception e) {
                log.error("Error while crawling source {}: {}", source.getName(), e.getMessage(), e);
            }
        }
    }

    /**
     * CRAWL 1 SOURCE
     * - Gọi list_url
     * - Lấy danh sách link bài theo list_item_selector
     * - Với mỗi link: nếu chưa có trong Post -> crawl chi tiết
     */
    private void crawlSource(Source source) throws Exception {
        log.info("Crawling source {} - {}", source.getName(), source.getListUrl());

        Document doc = Jsoup.connect(source.getListUrl()).userAgent("Mozilla/5.0 (compatible; SeoCrawlerBot/1.0)").timeout(15000).get();

        // Lấy tất cả element match selector: VD: "h3.title-news a"
        Elements items = doc.select(source.getListItemSelector());
        log.info("Source {}: found {} items from list page", source.getName(), items.size());

        for (Element item : items) {
            String href = item.attr(source.getLinkAttr()); // VD: attr("href")
            if (!StringUtils.hasText(href)) {
                continue;
            }
            String url = normalizeUrl(source, href);

            // Nếu Post đã tồn tại với originUrl này => bỏ qua
            if (postRepository.existsByOriginUrl(url)) {
                saveLog(source, url, CrawlStatus.SKIPPED, "Already exists");
                continue;
            }

            // Chưa có => crawl trang chi tiết & lưu Post
            fetchAndSavePostDetail(source, url);
        }
    }

    /**
     * Chuẩn hoá URL
     * - Nếu href đã là absolute (bắt đầu bằng http) => giữ nguyên
     * - Nếu là relative => ghép với base_url từ Source
     */
    private String normalizeUrl(Source source, String href) {
        if (href.startsWith("http://") || href.startsWith("https://")) {
            return href;
        }
        String base = source.getBaseUrl();
        if (base.endsWith("/") && href.startsWith("/")) {
            return base.substring(0, base.length() - 1) + href;
        }
        if (!base.endsWith("/") && !href.startsWith("/")) {
            return base + "/" + href;
        }
        return base + href;
    }

    /**
     * GỌI TRANG CHI TIẾT & LƯU POST
     * - Lấy title, content, thumbnail theo selector đã cấu hình trong Source
     * - Gắn Tenant từ Source => đảm bảo multi-tenant
     * - Đặt status = PENDING:
     *     + Cho phép admin/tenant vào duyệt & edit trước khi PUBLISHED
     */
    private void fetchAndSavePostDetail(Source source, String url) {
        try {
            Document doc = Jsoup.connect(url).userAgent("Mozilla/5.0 (compatible; SeoCrawlerBot/1.0)").timeout(15000).get();

            String title = selectText(doc, source.getTitleSelector());
            String contentHtml = selectHtml(doc, source.getContentSelector());
            String thumb = selectAttr(doc, source.getThumbnailSelector(), "src");

            Post post = new Post();
            post.setTenant(source.getTenant()); // MULTI-TENANT: bài thuộc tenant của source
            post.setSource(source); // để biết crawl từ đâu
            post.setOriginUrl(url);
            post.setTitle(title != null ? title : url);
            post.setContent(contentHtml);
            post.setContentRaw(doc.html());
            post.setThumbnail(thumb);
            post.setStatus(PostStatus.PENDING); // để admin duyệt => FE public chỉ show PUBLISHED
            post.setPublishedAt(Instant.now());
            post.setViewCount(0L);

            postRepository.save(post);
            saveLog(source, url, CrawlStatus.PUSHED_QUEUE, null);
        } catch (Exception e) {
            log.error("Error fetching detail for {}: {}", url, e.getMessage());
            saveLog(source, url, CrawlStatus.ERROR, e.getMessage());
        }
    }

    // Helpers: đọc text/html/attr theo selector, tránh lặp code

    private String selectText(Document doc, String selector) {
        if (!StringUtils.hasText(selector)) return null;
        Element el = doc.selectFirst(selector);
        return el != null ? el.text() : null;
    }

    private String selectHtml(Document doc, String selector) {
        if (!StringUtils.hasText(selector)) return null;
        Element el = doc.selectFirst(selector);
        return el != null ? el.html() : null;
    }

    private String selectAttr(Document doc, String selector, String attr) {
        if (!StringUtils.hasText(selector)) return null;
        Element el = doc.selectFirst(selector);
        return el != null ? el.attr(attr) : null;
    }

    /**
     * Lưu log crawl
     * - Giúp kiểm tra sau này: link nào mới, link nào lỗi, v.v.
     * - Nếu entity CrawlLog có tenant/source thì gắn vào
     */
    private void saveLog(Source source, String url, CrawlStatus status, String error) {
        CrawlLog logEntry = new CrawlLog();
        logEntry.setUrl(url);
        logEntry.setStatus(status);
        logEntry.setCrawledAt(Instant.now());

        try {
            // nếu CrawlLog có setTenant(Tenant) thì set luôn
            CrawlLog.class.getMethod("setTenant", source.getTenant().getClass());
            logEntry.setTenant(source.getTenant());
        } catch (NoSuchMethodException | NullPointerException ignored) {}

        try {
            // nếu CrawlLog có setSource(Source) thì set luôn
            CrawlLog.class.getMethod("setSource", Source.class);
            logEntry.setSource(source);
        } catch (NoSuchMethodException ignored) {}

        if (error != null) {
            logEntry.setErrorMessage(error.length() > 400 ? error.substring(0, 400) : error);
        }

        crawlLogRepository.save(logEntry);
    }
}
