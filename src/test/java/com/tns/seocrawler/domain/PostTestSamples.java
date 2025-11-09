package com.tns.seocrawler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class PostTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Post getPostSample1() {
        return new Post().id(1L).originUrl("originUrl1").title("title1").slug("slug1").thumbnail("thumbnail1").viewCount(1L);
    }

    public static Post getPostSample2() {
        return new Post().id(2L).originUrl("originUrl2").title("title2").slug("slug2").thumbnail("thumbnail2").viewCount(2L);
    }

    public static Post getPostRandomSampleGenerator() {
        return new Post()
            .id(longCount.incrementAndGet())
            .originUrl(UUID.randomUUID().toString())
            .title(UUID.randomUUID().toString())
            .slug(UUID.randomUUID().toString())
            .thumbnail(UUID.randomUUID().toString())
            .viewCount(longCount.incrementAndGet());
    }
}
