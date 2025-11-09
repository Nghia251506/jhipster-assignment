package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.AppUser;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.domain.User;
import com.tns.seocrawler.service.dto.AppUserDTO;
import com.tns.seocrawler.service.dto.TenantDTO;
import com.tns.seocrawler.service.dto.UserDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AppUser} and its DTO {@link AppUserDTO}.
 */
@Mapper(componentModel = "spring")
public interface AppUserMapper extends EntityMapper<AppUserDTO, AppUser> {
    @Mapping(target = "user", source = "user", qualifiedByName = "userLogin")
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantCode")
    AppUserDTO toDto(AppUser s);

    @Named("userLogin")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "login", source = "login")
    UserDTO toDtoUserLogin(User user);

    @Named("tenantCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TenantDTO toDtoTenantCode(Tenant tenant);
}
