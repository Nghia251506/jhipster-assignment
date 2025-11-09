package com.tns.seocrawler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CrawlLogTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static CrawlLog getCrawlLogSample1() {
        return new CrawlLog().id(1L).url("url1").errorMessage("errorMessage1");
    }

    public static CrawlLog getCrawlLogSample2() {
        return new CrawlLog().id(2L).url("url2").errorMessage("errorMessage2");
    }

    public static CrawlLog getCrawlLogRandomSampleGenerator() {
        return new CrawlLog().id(longCount.incrementAndGet()).url(UUID.randomUUID().toString()).errorMessage(UUID.randomUUID().toString());
    }
}
