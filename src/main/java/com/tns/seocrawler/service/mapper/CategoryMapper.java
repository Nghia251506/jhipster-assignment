package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.Category;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.service.dto.CategoryDTO;
import com.tns.seocrawler.service.dto.TenantDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Category} and its DTO {@link CategoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantCode")
    CategoryDTO toDto(Category s);

    @Named("tenantCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TenantDTO toDtoTenantCode(Tenant tenant);
}
