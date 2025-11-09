package com.tns.seocrawler.service.mapper;

import static com.tns.seocrawler.domain.CrawlLogAsserts.*;
import static com.tns.seocrawler.domain.CrawlLogTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrawlLogMapperTest {

    private CrawlLogMapper crawlLogMapper;

    @BeforeEach
    void setUp() {
        crawlLogMapper = new CrawlLogMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getCrawlLogSample1();
        var actual = crawlLogMapper.toEntity(crawlLogMapper.toDto(expected));
        assertCrawlLogAllPropertiesEquals(expected, actual);
    }
}
