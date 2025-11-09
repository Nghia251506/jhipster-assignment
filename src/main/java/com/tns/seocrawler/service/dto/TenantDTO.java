package com.tns.seocrawler.service.dto;

import com.tns.seocrawler.domain.enumeration.TenantStatus;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.tns.seocrawler.domain.Tenant} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class TenantDTO implements Serializable {

    private Long id;

    @NotNull
    private String code;

    @NotNull
    private String name;

    private String contactEmail;

    private String contactPhone;

    @NotNull
    private Integer maxUsers;

    @NotNull
    private TenantStatus status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public Integer getMaxUsers() {
        return maxUsers;
    }

    public void setMaxUsers(Integer maxUsers) {
        this.maxUsers = maxUsers;
    }

    public TenantStatus getStatus() {
        return status;
    }

    public void setStatus(TenantStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantDTO)) {
            return false;
        }

        TenantDTO tenantDTO = (TenantDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tenantDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "TenantDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", contactEmail='" + getContactEmail() + "'" +
            ", contactPhone='" + getContactPhone() + "'" +
            ", maxUsers=" + getMaxUsers() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
