package com.tns.seocrawler.service.dto;

import com.tns.seocrawler.domain.enumeration.CrawlStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.tns.seocrawler.domain.CrawlLog} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CrawlLogDTO implements Serializable {

    private Long id;

    @NotNull
    private String url;

    @NotNull
    private CrawlStatus status;

    private String errorMessage;

    @NotNull
    private Instant crawledAt;

    private TenantDTO tenant;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public CrawlStatus getStatus() {
        return status;
    }

    public void setStatus(CrawlStatus status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Instant getCrawledAt() {
        return crawledAt;
    }

    public void setCrawledAt(Instant crawledAt) {
        this.crawledAt = crawledAt;
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
        if (!(o instanceof CrawlLogDTO)) {
            return false;
        }

        CrawlLogDTO crawlLogDTO = (CrawlLogDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, crawlLogDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CrawlLogDTO{" +
            "id=" + getId() +
            ", url='" + getUrl() + "'" +
            ", status='" + getStatus() + "'" +
            ", errorMessage='" + getErrorMessage() + "'" +
            ", crawledAt='" + getCrawledAt() + "'" +
            ", tenant=" + getTenant() +
            "}";
    }
}
