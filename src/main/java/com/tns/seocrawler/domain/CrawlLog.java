package com.tns.seocrawler.domain;

import com.tns.seocrawler.domain.enumeration.CrawlStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A CrawlLog.
 */
@Entity
@Table(name = "crawl_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CrawlLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "url", nullable = false)
    private String url;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CrawlStatus status;

    @Column(name = "error_message")
    private String errorMessage;

    @NotNull
    @Column(name = "crawled_at", nullable = false)
    private Instant crawledAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Tenant tenant;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CrawlLog id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public CrawlLog url(String url) {
        this.setUrl(url);
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CrawlStatus getStatus() {
        return this.status;
    }

    public CrawlLog status(CrawlStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public CrawlLog errorMessage(String errorMessage) {
        this.setErrorMessage(errorMessage);
        return this;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getCrawledAt() {
        return this.crawledAt;
    }

    public CrawlLog crawledAt(Instant crawledAt) {
        this.setCrawledAt(crawledAt);
        return this;
    }

    public void setCrawledAt(Instant crawledAt) {
        this.crawledAt = crawledAt;
    }

    public Tenant getTenant() {
        return this.tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public CrawlLog tenant(Tenant tenant) {
        this.setTenant(tenant);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CrawlLog)) {
            return false;
        }
        return getId() != null && getId().equals(((CrawlLog) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrawlLog{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", crawledAt='" + getCrawledAt() + "'" +
            "}";
    }
}
