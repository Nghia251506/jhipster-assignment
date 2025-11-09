package com.tns.seocrawler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SiteSettingTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SiteSetting getSiteSettingSample1() {
        return new SiteSetting().id(1L).siteTitle("siteTitle1").gaTrackingId("gaTrackingId1");
    }

    public static SiteSetting getSiteSettingSample2() {
        return new SiteSetting().id(2L).siteTitle("siteTitle2").gaTrackingId("gaTrackingId2");
    }

    public static SiteSetting getSiteSettingRandomSampleGenerator() {
        return new SiteSetting()
            .id(longCount.incrementAndGet())
            .siteTitle(UUID.randomUUID().toString())
            .gaTrackingId(UUID.randomUUID().toString());
    }
}
