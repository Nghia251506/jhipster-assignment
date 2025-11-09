package com.tns.seocrawler.domain;

import static com.tns.seocrawler.domain.AppUserTestSamples.*;
import static com.tns.seocrawler.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tns.seocrawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppUserTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AppUser.class);
        AppUser appUser1 = getAppUserSample1();
        AppUser appUser2 = new AppUser();
        assertThat(appUser1).isNotEqualTo(appUser2);

        appUser2.setId(appUser1.getId());
        assertThat(appUser1).isEqualTo(appUser2);

        appUser2 = getAppUserSample2();
        assertThat(appUser1).isNotEqualTo(appUser2);
    }

    @Test
    void tenantTest() {
        AppUser appUser = getAppUserRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        appUser.setTenant(tenantBack);
        assertThat(appUser.getTenant()).isEqualTo(tenantBack);

        appUser.tenant(null);
        assertThat(appUser.getTenant()).isNull();
    }
}
