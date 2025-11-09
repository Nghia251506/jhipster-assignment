package com.tns.seocrawler.domain;

import static com.tns.seocrawler.domain.SiteSettingTestSamples.*;
import static com.tns.seocrawler.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tns.seocrawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteSettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteSetting.class);
        SiteSetting siteSetting1 = getSiteSettingSample1();
        SiteSetting siteSetting2 = new SiteSetting();
        assertThat(siteSetting1).isNotEqualTo(siteSetting2);

        siteSetting2.setId(siteSetting1.getId());
        assertThat(siteSetting1).isEqualTo(siteSetting2);

        siteSetting2 = getSiteSettingSample2();
        assertThat(siteSetting1).isNotEqualTo(siteSetting2);
    }

    @Test
    void tenantTest() {
        SiteSetting siteSetting = getSiteSettingRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        siteSetting.setTenant(tenantBack);
        assertThat(siteSetting.getTenant()).isEqualTo(tenantBack);

        siteSetting.tenant(null);
        assertThat(siteSetting.getTenant()).isNull();
    }
}
