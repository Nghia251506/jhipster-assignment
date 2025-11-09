package com.tns.seocrawler.domain;

import static com.tns.seocrawler.domain.CategoryTestSamples.*;
import static com.tns.seocrawler.domain.PostTestSamples.*;
import static com.tns.seocrawler.domain.SourceTestSamples.*;
import static com.tns.seocrawler.domain.TagTestSamples.*;
import static com.tns.seocrawler.domain.TenantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.tns.seocrawler.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class PostTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Post.class);
        Post post1 = getPostSample1();
        Post post2 = new Post();
        assertThat(post1).isNotEqualTo(post2);

        post2.setId(post1.getId());
        assertThat(post1).isEqualTo(post2);

        post2 = getPostSample2();
        assertThat(post1).isNotEqualTo(post2);
    }

    @Test
    void tenantTest() {
        Post post = getPostRandomSampleGenerator();
        Tenant tenantBack = getTenantRandomSampleGenerator();

        post.setTenant(tenantBack);
        assertThat(post.getTenant()).isEqualTo(tenantBack);

        post.tenant(null);
        assertThat(post.getTenant()).isNull();
    }

    @Test
    void sourceTest() {
        Post post = getPostRandomSampleGenerator();
        Source sourceBack = getSourceRandomSampleGenerator();

        post.setSource(sourceBack);
        assertThat(post.getSource()).isEqualTo(sourceBack);

        post.source(null);
        assertThat(post.getSource()).isNull();
    }

    @Test
    void categoryTest() {
        Post post = getPostRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        post.setCategory(categoryBack);
        assertThat(post.getCategory()).isEqualTo(categoryBack);

        post.category(null);
        assertThat(post.getCategory()).isNull();
    }

    @Test
    void tagsTest() {
        Post post = getPostRandomSampleGenerator();
        Tag tagBack = getTagRandomSampleGenerator();

        post.addTags(tagBack);
        assertThat(post.getTags()).containsOnly(tagBack);

        post.removeTags(tagBack);
        assertThat(post.getTags()).doesNotContain(tagBack);

        post.tags(new HashSet<>(Set.of(tagBack)));
        assertThat(post.getTags()).containsOnly(tagBack);

        post.setTags(new HashSet<>());
        assertThat(post.getTags()).doesNotContain(tagBack);
    }
}
