package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.CrawlLog;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.service.dto.CrawlLogDTO;
import com.tns.seocrawler.service.dto.TenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link CrawlLog} and its DTO {@link CrawlLogDTO}.
 */
@Mapper(componentModel = "spring")
public interface CrawlLogMapper extends EntityMapper<CrawlLogDTO, CrawlLog> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantCode")
    CrawlLogDTO toDto(CrawlLog s);

    @Named("tenantCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TenantDTO toDtoTenantCode(Tenant tenant);
}
