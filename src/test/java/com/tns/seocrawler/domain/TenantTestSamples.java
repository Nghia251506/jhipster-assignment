package com.tns.seocrawler.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class TenantTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Tenant getTenantSample1() {
        return new Tenant().id(1L).code("code1").name("name1").contactEmail("contactEmail1").contactPhone("contactPhone1").maxUsers(1);
    }

    public static Tenant getTenantSample2() {
        return new Tenant().id(2L).code("code2").name("name2").contactEmail("contactEmail2").contactPhone("contactPhone2").maxUsers(2);
    }

    public static Tenant getTenantRandomSampleGenerator() {
        return new Tenant()
            .id(longCount.incrementAndGet())
            .code(UUID.randomUUID().toString())
            .name(UUID.randomUUID().toString())
            .contactEmail(UUID.randomUUID().toString())
            .contactPhone(UUID.randomUUID().toString())
            .maxUsers(intCount.incrementAndGet());
    }
}
