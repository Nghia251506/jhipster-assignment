package com.tns.seocrawler.web.rest;

import com.tns.seocrawler.service.CrawlerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * REST endpoint cho admin trigger crawler.
 */
@RestController
@RequestMapping("/api/admin/crawler")
public class CrawlerResource {

    private final Logger log = LoggerFactory.getLogger(CrawlerResource.class);
    private final CrawlerService crawlerService;

    public CrawlerResource(CrawlerService crawlerService) {
        this.crawlerService = crawlerService;
    }

    @PostMapping("/run")
    @PreAuthorize("hasAuthority(\"ROLE_ADMIN\")")
    public ResponseEntity<Void> runCrawler() {
        log.info("Manual trigger: crawl all sources");
        crawlerService.crawlAllSources();
        return ResponseEntity.accepted().build();
    }
}
