package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.Category;
import com.tns.seocrawler.domain.Source;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.service.dto.CategoryDTO;
import com.tns.seocrawler.service.dto.SourceDTO;
import com.tns.seocrawler.service.dto.TenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Source} and its DTO {@link SourceDTO}.
 */
@Mapper(componentModel = "spring")
public interface SourceMapper extends EntityMapper<SourceDTO, Source> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantCode")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryName")
    SourceDTO toDto(Source s);

    @Named("tenantCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TenantDTO toDtoTenantCode(Tenant tenant);

    @Named("categoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTO toDtoCategoryName(Category category);
}
