package com.tns.seocrawler.domain;

import static com.tns.seocrawler.domain.CategoryTestSamples.*;
import static com.tns.seocrawler.domain.SourceTestSamples.*;
import static com.tns.seocrawler.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tns.seocrawler.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SourceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Source.class);
        Source source1 = getSourceSample1();
        Source source2 = new Source();
        assertThat(source1).isNotEqualTo(source2);

        source2.setId(source1.getId());
        assertThat(source1).isEqualTo(source2);

        source2 = getSourceSample2();
        assertThat(source1).isNotEqualTo(source2);
    }

    @Test
    void tenantTest() {
        Source source = getSourceRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        source.setTenant(tenantBack);
        assertThat(source.getTenant()).isEqualTo(tenantBack);

        source.tenant(null);
        assertThat(source.getTenant()).isNull();
    }

    @Test
    void categoryTest() {
        Source source = getSourceRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        source.setCategory(categoryBack);
        assertThat(source.getCategory()).isEqualTo(categoryBack);

        source.category(null);
        assertThat(source.getCategory()).isNull();
    }
}
