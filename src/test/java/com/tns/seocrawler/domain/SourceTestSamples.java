package com.tns.seocrawler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SourceTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Source getSourceSample1() {
        return new Source()
            .id(1L)
            .name("name1")
            .baseUrl("baseUrl1")
            .listUrl("listUrl1")
            .listItemSelector("listItemSelector1")
            .linkAttr("linkAttr1")
            .titleSelector("titleSelector1")
            .contentSelector("contentSelector1")
            .thumbnailSelector("thumbnailSelector1")
            .authorSelector("authorSelector1")
            .note("note1");
    }

    public static Source getSourceSample2() {
        return new Source()
            .id(2L)
            .name("name2")
            .baseUrl("baseUrl2")
            .listUrl("listUrl2")
            .listItemSelector("listItemSelector2")
            .linkAttr("linkAttr2")
            .titleSelector("titleSelector2")
            .contentSelector("contentSelector2")
            .thumbnailSelector("thumbnailSelector2")
            .authorSelector("authorSelector2")
            .note("note2");
    }

    public static Source getSourceRandomSampleGenerator() {
        return new Source()
            .id(longCount.incrementAndGet())
            .name(UUID.randomUUID().toString())
            .baseUrl(UUID.randomUUID().toString())
            .listUrl(UUID.randomUUID().toString())
            .listItemSelector(UUID.randomUUID().toString())
            .linkAttr(UUID.randomUUID().toString())
            .titleSelector(UUID.randomUUID().toString())
            .contentSelector(UUID.randomUUID().toString())
            .thumbnailSelector(UUID.randomUUID().toString())
            .authorSelector(UUID.randomUUID().toString())
            .note(UUID.randomUUID().toString());
    }
}
