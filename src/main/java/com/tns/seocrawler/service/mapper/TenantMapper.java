package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.service.dto.TenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tenant} and its DTO {@link TenantDTO}.
 */
@Mapper(componentModel = "spring")
public interface TenantMapper extends EntityMapper<TenantDTO, Tenant> {}
