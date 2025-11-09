package com.tns.seocrawler.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.tns.seocrawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrawlLogDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CrawlLogDTO.class);
        CrawlLogDTO crawlLogDTO1 = new CrawlLogDTO();
        crawlLogDTO1.setId(1L);
        CrawlLogDTO crawlLogDTO2 = new CrawlLogDTO();
        assertThat(crawlLogDTO1).isNotEqualTo(crawlLogDTO2);
        crawlLogDTO2.setId(crawlLogDTO1.getId());
        assertThat(crawlLogDTO1).isEqualTo(crawlLogDTO2);
        crawlLogDTO2.setId(2L);
        assertThat(crawlLogDTO1).isNotEqualTo(crawlLogDTO2);
        crawlLogDTO1.setId(null);
        assertThat(crawlLogDTO1).isNotEqualTo(crawlLogDTO2);
    }
}
