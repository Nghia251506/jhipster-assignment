package com.tns.seocrawler.service.dto;

import com.tns.seocrawler.domain.enumeration.PostStatus;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.tns.seocrawler.domain.Post} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PostDTO implements Serializable {

    private Long id;

    @NotNull
    private String originUrl;

    private String title;

    private String slug;

    @Lob
    private String summary;

    @Lob
    private String content;

    @Lob
    private String contentRaw;

    private String thumbnail;

    @NotNull
    private PostStatus status;

    private Instant publishedAt;

    @NotNull
    private Long viewCount;

    private TenantDTO tenant;

    private SourceDTO source;

    private CategoryDTO category;

    private Set<TagDTO> tags = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentRaw() {
        return contentRaw;
    }

    public void setContentRaw(String contentRaw) {
        this.contentRaw = contentRaw;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Instant getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Long getViewCount() {
        return viewCount;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    public SourceDTO getSource() {
        return source;
    }

    public void setSource(SourceDTO source) {
        this.source = source;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    public Set<TagDTO> getTags() {
        return tags;
    }

    public void setTags(Set<TagDTO> tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PostDTO)) {
            return false;
        }

        PostDTO postDTO = (PostDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, postDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PostDTO{" +
            "id=" + getId() +
            ", originUrl='" + getOriginUrl() + "'" +
            ", title='" + getTitle() + "'" +
            ", slug='" + getSlug() + "'" +
            ", summary='" + getSummary() + "'" +
            ", content='" + getContent() + "'" +
            ", contentRaw='" + getContentRaw() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", status='" + getStatus() + "'" +
            ", publishedAt='" + getPublishedAt() + "'" +
            ", viewCount=" + getViewCount() +
            ", tenant=" + getTenant() +
            ", source=" + getSource() +
            ", category=" + getCategory() +
            ", tags=" + getTags() +
            "}";
    }
}
