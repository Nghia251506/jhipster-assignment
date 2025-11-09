package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.SiteSetting;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.service.dto.SiteSettingDTO;
import com.tns.seocrawler.service.dto.TenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link SiteSetting} and its DTO {@link SiteSettingDTO}.
 */
@Mapper(componentModel = "spring")
public interface SiteSettingMapper extends EntityMapper<SiteSettingDTO, SiteSetting> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantCode")
    SiteSettingDTO toDto(SiteSetting s);

    @Named("tenantCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TenantDTO toDtoTenantCode(Tenant tenant);
}
