package com.tns.seocrawler.domain;

import static com.tns.seocrawler.domain.CrawlLogTestSamples.*;
import static com.tns.seocrawler.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tns.seocrawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrawlLogTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrawlLog.class);
        CrawlLog crawlLog1 = getCrawlLogSample1();
        CrawlLog crawlLog2 = new CrawlLog();
        assertThat(crawlLog1).isNotEqualTo(crawlLog2);

        crawlLog2.setId(crawlLog1.getId());
        assertThat(crawlLog1).isEqualTo(crawlLog2);

        crawlLog2 = getCrawlLogSample2();
        assertThat(crawlLog1).isNotEqualTo(crawlLog2);
    }

    @Test
    void tenantTest() {
        CrawlLog crawlLog = getCrawlLogRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        crawlLog.setTenant(tenantBack);
        assertThat(crawlLog.getTenant()).isEqualTo(tenantBack);

        crawlLog.tenant(null);
        assertThat(crawlLog.getTenant()).isNull();
    }
}
