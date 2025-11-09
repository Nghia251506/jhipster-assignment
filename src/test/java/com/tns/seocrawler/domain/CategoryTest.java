package com.tns.seocrawler.domain;

import static com.tns.seocrawler.domain.CategoryTestSamples.*;
import static com.tns.seocrawler.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tns.seocrawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Category.class);
        Category category1 = getCategorySample1();
        Category category2 = new Category();
        assertThat(category1).isNotEqualTo(category2);

        category2.setId(category1.getId());
        assertThat(category1).isEqualTo(category2);

        category2 = getCategorySample2();
        assertThat(category1).isNotEqualTo(category2);
    }

    @Test
    void tenantTest() {
        Category category = getCategoryRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        category.setTenant(tenantBack);
        assertThat(category.getTenant()).isEqualTo(tenantBack);

        category.tenant(null);
        assertThat(category.getTenant()).isNull();
    }
}
