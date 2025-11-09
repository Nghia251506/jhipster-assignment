package com.tns.seocrawler.service.mapper;

import static com.tns.seocrawler.domain.SourceAsserts.*;
import static com.tns.seocrawler.domain.SourceTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SourceMapperTest {

    private SourceMapper sourceMapper;

    @BeforeEach
    void setUp() {
        sourceMapper = new SourceMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSourceSample1();
        var actual = sourceMapper.toEntity(sourceMapper.toDto(expected));
        assertSourceAllPropertiesEquals(expected, actual);
    }
}
