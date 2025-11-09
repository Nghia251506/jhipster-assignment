package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.Category;
import com.tns.seocrawler.domain.Post;
import com.tns.seocrawler.domain.Source;
import com.tns.seocrawler.domain.Tag;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.service.dto.CategoryDTO;
import com.tns.seocrawler.service.dto.PostDTO;
import com.tns.seocrawler.service.dto.SourceDTO;
import com.tns.seocrawler.service.dto.TagDTO;
import com.tns.seocrawler.service.dto.TenantDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Post} and its DTO {@link PostDTO}.
 */
@Mapper(componentModel = "spring")
public interface PostMapper extends EntityMapper<PostDTO, Post> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantCode")
    @Mapping(target = "source", source = "source", qualifiedByName = "sourceName")
    @Mapping(target = "category", source = "category", qualifiedByName = "categoryName")
    @Mapping(target = "tags", source = "tags", qualifiedByName = "tagNameSet")
    PostDTO toDto(Post s);

    @Mapping(target = "removeTags", ignore = true)
    Post toEntity(PostDTO postDTO);

    @Named("tenantCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TenantDTO toDtoTenantCode(Tenant tenant);

    @Named("sourceName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    SourceDTO toDtoSourceName(Source source);

    @Named("categoryName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    CategoryDTO toDtoCategoryName(Category category);

    @Named("tagName")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    TagDTO toDtoTagName(Tag tag);

    @Named("tagNameSet")
    default Set<TagDTO> toDtoTagNameSet(Set<Tag> tag) {
        return tag.stream().map(this::toDtoTagName).collect(Collectors.toSet());
    }
}
