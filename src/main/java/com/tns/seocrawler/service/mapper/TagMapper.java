package com.tns.seocrawler.service.mapper;

import com.tns.seocrawler.domain.Post;
import com.tns.seocrawler.domain.Tag;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.service.dto.PostDTO;
import com.tns.seocrawler.service.dto.TagDTO;
import com.tns.seocrawler.service.dto.TenantDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Tag} and its DTO {@link TagDTO}.
 */
@Mapper(componentModel = "spring")
public interface TagMapper extends EntityMapper<TagDTO, Tag> {
    @Mapping(target = "tenant", source = "tenant", qualifiedByName = "tenantCode")
    @Mapping(target = "posts", source = "posts", qualifiedByName = "postIdSet")
    TagDTO toDto(Tag s);

    @Mapping(target = "posts", ignore = true)
    @Mapping(target = "removePosts", ignore = true)
    Tag toEntity(TagDTO tagDTO);

    @Named("tenantCode")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "code", source = "code")
    TenantDTO toDtoTenantCode(Tenant tenant);

    @Named("postId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PostDTO toDtoPostId(Post post);

    @Named("postIdSet")
    default Set<PostDTO> toDtoPostIdSet(Set<Post> post) {
        return post.stream().map(this::toDtoPostId).collect(Collectors.toSet());
    }
}
