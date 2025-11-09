package com.tns.seocrawler.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteSetting.
 */
@Entity
@Table(name = "site_setting")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteSetting implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "site_title")
    private String siteTitle;

    @Lob
    @Column(name = "site_description")
    private String siteDescription;

    @Lob
    @Column(name = "site_keywords")
    private String siteKeywords;

    @Column(name = "ga_tracking_id")
    private String gaTrackingId;

    @Lob
    @Column(name = "banner_top")
    private String bannerTop;

    @Lob
    @Column(name = "banner_sidebar")
    private String bannerSidebar;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SiteSetting id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteTitle() {
        return this.siteTitle;
    }

    public SiteSetting siteTitle(String siteTitle) {
        this.setSiteTitle(siteTitle);
        return this;
    }

    public void setSiteTitle(String siteTitle) {
        this.siteTitle = siteTitle;
    }

    public String getSiteDescription() {
        return this.siteDescription;
    }

    public SiteSetting siteDescription(String siteDescription) {
        this.setSiteDescription(siteDescription);
        return this;
    }

    public void setSiteDescription(String siteDescription) {
        this.siteDescription = siteDescription;
    }

    public String getSiteKeywords() {
        return this.siteKeywords;
    }

    public SiteSetting siteKeywords(String siteKeywords) {
        this.setSiteKeywords(siteKeywords);
        return this;
    }

    public void setSiteKeywords(String siteKeywords) {
        this.siteKeywords = siteKeywords;
    }

    public String getGaTrackingId() {
        return this.gaTrackingId;
    }

    public SiteSetting gaTrackingId(String gaTrackingId) {
        this.setGaTrackingId(gaTrackingId);
        return this;
    }

    public void setGaTrackingId(String gaTrackingId) {
        this.gaTrackingId = gaTrackingId;
    }

    public String getBannerTop() {
        return this.bannerTop;
    }

    public SiteSetting bannerTop(String bannerTop) {
        this.setBannerTop(bannerTop);
        return this;
    }

    public void setBannerTop(String bannerTop) {
        this.bannerTop = bannerTop;
    }

    public String getBannerSidebar() {
        return this.bannerSidebar;
    }

    public SiteSetting bannerSidebar(String bannerSidebar) {
        this.setBannerSidebar(bannerSidebar);
        return this;
    }

    public void setBannerSidebar(String bannerSidebar) {
        this.bannerSidebar = bannerSidebar;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public SiteSetting tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteSetting)) {
            return false;
        }
        return getId() != null && getId().equals(((SiteSetting) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteSetting{" +
            "id=" + getId() +
            ", siteTitle='" + getSiteTitle() + "'" +
            ", siteDescription='" + getSiteDescription() + "'" +
            ", siteKeywords='" + getSiteKeywords() + "'" +
            ", gaTrackingId='" + getGaTrackingId() + "'" +
            ", bannerTop='" + getBannerTop() + "'" +
            ", bannerSidebar='" + getBannerSidebar() + "'" +
            "}";
    }
}
