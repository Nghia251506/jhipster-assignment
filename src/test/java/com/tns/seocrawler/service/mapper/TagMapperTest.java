package com.tns.seocrawler.service.mapper;

import static com.tns.seocrawler.domain.TagAsserts.*;
import static com.tns.seocrawler.domain.TagTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TagMapperTest {

    private TagMapper tagMapper;

    @BeforeEach
    void setUp() {
        tagMapper = new TagMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getTagSample1();
        var actual = tagMapper.toEntity(tagMapper.toDto(expected));
        assertTagAllPropertiesEquals(expected, actual);
    }
}
