package com.tns.seocrawler.service.dto;

import jakarta.persistence.Lob;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tns.seocrawler.domain.SiteSetting} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteSettingDTO implements Serializable {

    private Long id;

    private String siteTitle;

    @Lob
    private String siteDescription;

    @Lob
    private String siteKeywords;

    private String gaTrackingId;

    @Lob
    private String bannerTop;

    @Lob
    private String bannerSidebar;

    private TenantDTO tenant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteTitle() {
        return siteTitle;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getSiteDescription() {
        return siteDescription;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteKeywords() {
        return siteKeywords;
    }

    public void setSiteKeywords(String siteKeywords) {
        this.siteKeywords = siteKeywords;
    }

    public String getGaTrackingId() {
        return gaTrackingId;
    }

    public void setGaTrackingId(String gaTrackingId) {
        this.gaTrackingId = gaTrackingId;
    }

    public String getBannerTop() {
        return bannerTop;
    }

    public void setBannerTop(String bannerTop) {
        this.bannerTop = bannerTop;
    }

    public String getBannerSidebar() {
        return bannerSidebar;
    }

    public void setBannerSidebar(String bannerSidebar) {
        this.bannerSidebar = bannerSidebar;
    }

    public TenantDTO getTenant() {
        return tenant;
    }

    public void setTenant(TenantDTO tenant) {
        this.tenant = tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteSettingDTO)) {
            return false;
        }

        SiteSettingDTO siteSettingDTO = (SiteSettingDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, siteSettingDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteSettingDTO{" +
            "id=" + getId() +
            ", siteTitle='" + getSiteTitle() + "'" +
            ", siteDescription='" + getSiteDescription() + "'" +
            ", siteKeywords='" + getSiteKeywords() + "'" +
            ", gaTrackingId='" + getGaTrackingId() + "'" +
            ", bannerTop='" + getBannerTop() + "'" +
            ", bannerSidebar='" + getBannerSidebar() + "'" +
            ", tenant=" + getTenant() +
            "}";
    }
}
