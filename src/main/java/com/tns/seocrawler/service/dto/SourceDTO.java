package com.tns.seocrawler.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tns.seocrawler.domain.Source} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SourceDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String baseUrl;

    @NotNull
    private String listUrl;

    @NotNull
    private String listItemSelector;

    @NotNull
    private String linkAttr;

    private String titleSelector;

    private String contentSelector;

    private String thumbnailSelector;

    private String authorSelector;

    @NotNull
    private Boolean isActive;

    private String note;

    private TenantDTO tenant;

    private CategoryDTO category;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getListUrl() {
        return listUrl;
    }

    public void setListUrl(String listUrl) {
        this.listUrl = listUrl;
    }

    public String getListItemSelector() {
        return listItemSelector;
    }

    public void setListItemSelector(String listItemSelector) {
        this.listItemSelector = listItemSelector;
    }

    public String getLinkAttr() {
        return linkAttr;
    }

    public void setLinkAttr(String linkAttr) {
        this.linkAttr = linkAttr;
    }

    public String getTitleSelector() {
        return titleSelector;
    }

    public void setTitleSelector(String titleSelector) {
        this.titleSelector = titleSelector;
    }

    public String getContentSelector() {
        return contentSelector;
    }

    public void setContentSelector(String contentSelector) {
        this.contentSelector = contentSelector;
    }

    public String getThumbnailSelector() {
        return thumbnailSelector;
    }

    public void setThumbnailSelector(String thumbnailSelector) {
        this.thumbnailSelector = thumbnailSelector;
    }

    public String getAuthorSelector() {
        return authorSelector;
    }

    public void setAuthorSelector(String authorSelector) {
        this.authorSelector = authorSelector;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    public CategoryDTO getCategory() {
        return category;
    }

    public void setCategory(CategoryDTO category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SourceDTO)) {
            return false;
        }

        SourceDTO sourceDTO = (SourceDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, sourceDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SourceDTO{" +
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
            ", tenant=" + getTenant() +
            ", category=" + getCategory() +
            "}";
    }
}
