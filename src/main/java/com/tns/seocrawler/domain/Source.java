package com.tns.seocrawler.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Source.
 */
@Entity
@Table(name = "source")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Source implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "base_url", nullable = false)
    private String baseUrl;

    @NotNull
    @Column(name = "list_url", nullable = false)
    private String listUrl;

    @NotNull
    @Column(name = "list_item_selector", nullable = false)
    private String listItemSelector;

    @NotNull
    @Column(name = "link_attr", nullable = false)
    private String linkAttr;

    @Column(name = "title_selector")
    private String titleSelector;

    @Column(name = "content_selector")
    private String contentSelector;

    @Column(name = "thumbnail_selector")
    private String thumbnailSelector;

    @Column(name = "author_selector")
    private String authorSelector;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "note")
    private String note;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "tenant" }, allowSetters = true)
    private Category category;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Source id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Source name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public Source baseUrl(String baseUrl) {
        this.setBaseUrl(baseUrl);
        return this;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getListUrl() {
        return this.listUrl;
    }

    public Source listUrl(String listUrl) {
        this.setListUrl(listUrl);
        return this;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getListItemSelector() {
        return this.listItemSelector;
    }

    public Source listItemSelector(String listItemSelector) {
        this.setListItemSelector(listItemSelector);
        return this;
    }

    public void setListItemSelector(String listItemSelector) {
        this.listItemSelector = listItemSelector;
    }

    public String getLinkAttr() {
        return this.linkAttr;
    }

    public Source linkAttr(String linkAttr) {
        this.setLinkAttr(linkAttr);
        return this;
    }

    public void setLinkAttr(String linkAttr) {
        this.linkAttr = linkAttr;
    }

    public String getTitleSelector() {
        return this.titleSelector;
    }

    public Source titleSelector(String titleSelector) {
        this.setTitleSelector(titleSelector);
        return this;
    }

    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    public String getContentSelector() {
        return this.contentSelector;
    }

    public Source contentSelector(String contentSelector) {
        this.setContentSelector(contentSelector);
        return this;
    }

    public void setContentSelector(String contentSelector) {
        this.contentSelector = contentSelector;
    }

    public String getThumbnailSelector() {
        return this.thumbnailSelector;
    }

    public Source thumbnailSelector(String thumbnailSelector) {
        this.setThumbnailSelector(thumbnailSelector);
        return this;
    }

    public void setThumbnailSelector(String thumbnailSelector) {
        this.thumbnailSelector = thumbnailSelector;
    }

    public String getAuthorSelector() {
        return this.authorSelector;
    }

    public Source authorSelector(String authorSelector) {
        this.setAuthorSelector(authorSelector);
        return this;
    }

    public void setAuthorSelector(String authorSelector) {
        this.authorSelector = authorSelector;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Source isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getNote() {
        return this.note;
    }

    public Source note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public Source tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Source category(Category category) {
        this.setCategory(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Source)) {
            return false;
        }
        return getId() != null && getId().equals(((Source) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Source{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", baseUrl='" + getBaseUrl() + "'" +
            ", listUrl='" + getListUrl() + "'" +
            ", listItemSelector='" + getListItemSelector() + "'" +
            ", linkAttr='" + getLinkAttr() + "'" +
            ", titleSelector='" + getTitleSelector() + "'" +
            ", contentSelector='" + getContentSelector() + "'" +
            ", thumbnailSelector='" + getThumbnailSelector() + "'" +
            ", authorSelector='" + getAuthorSelector() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", note='" + getNote() + "'" +
            "}";
    }
}
