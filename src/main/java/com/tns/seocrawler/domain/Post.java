package com.tns.seocrawler.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tns.seocrawler.domain.enumeration.PostStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Post.
 */
@Entity
@Table(name = "post")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Post implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "origin_url", nullable = false)
    private String originUrl;

    @Column(name = "title")
    private String title;

    @Column(name = "slug")
    private String slug;

    @Lob
    @Column(name = "summary")
    private String summary;

    @Lob
    @Column(name = "content")
    private String content;

    @Lob
    @Column(name = "content_raw")
    private String contentRaw;

    @Column(name = "thumbnail")
    private String thumbnail;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PostStatus status;

    @Column(name = "published_at")
    private Instant publishedAt;

    @NotNull
    @Column(name = "view_count", nullable = false)
    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenant", "category" }, allowSetters = true)
    private Source source;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenant" }, allowSetters = true)
    private Category category;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rel_post__tags", joinColumns = @JoinColumn(name = "post_id"), inverseJoinColumns = @JoinColumn(name = "tags_id"))
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "tenant", "posts" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Post id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginUrl() {
        return this.originUrl;
    }

    public Post originUrl(String originUrl) {
        this.setOriginUrl(originUrl);
        return this;
    }

    public void setOriginUrl(String originUrl) {
        this.originUrl = originUrl;
    }

    public String getTitle() {
        return this.title;
    }

    public Post title(String title) {
        this.setTitle(title);
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSlug() {
        return this.slug;
    }

    public Post slug(String slug) {
        this.setSlug(slug);
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSummary() {
        return this.summary;
    }

    public Post summary(String summary) {
        this.setSummary(summary);
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return this.content;
    }

    public Post content(String content) {
        this.setContent(content);
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContentRaw() {
        return this.contentRaw;
    }

    public Post contentRaw(String contentRaw) {
        this.setContentRaw(contentRaw);
        return this;
    }

    public void setContentRaw(String contentRaw) {
        this.contentRaw = contentRaw;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public Post thumbnail(String thumbnail) {
        this.setThumbnail(thumbnail);
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public PostStatus getStatus() {
        return this.status;
    }

    public Post status(PostStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
    }

    public Instant getPublishedAt() {
        return this.publishedAt;
    }

    public Post publishedAt(Instant publishedAt) {
        this.setPublishedAt(publishedAt);
        return this;
    }

    public void setPublishedAt(Instant publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Long getViewCount() {
        return this.viewCount;
    }

    public Post viewCount(Long viewCount) {
        this.setViewCount(viewCount);
        return this;
    }

    public void setViewCount(Long viewCount) {
        this.viewCount = viewCount;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Post tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    public Source getSource() {
        return this.source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Post source(Source source) {
        this.setSource(source);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Post category(Category category) {
        this.setCategory(category);
        return this;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    public Post tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Post addTags(Tag tag) {
        this.tags.add(tag);
        return this;
    }

    public Post removeTags(Tag tag) {
        this.tags.remove(tag);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Post)) {
            return false;
        }
        return getId() != null && getId().equals(((Post) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Post{" +
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
            "}";
    }
}
