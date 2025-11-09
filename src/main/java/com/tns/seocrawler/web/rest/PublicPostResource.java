package com.tns.seocrawler.web.rest;

import com.tns.seocrawler.domain.Post;
import com.tns.seocrawler.domain.enumeration.PostStatus;
import com.tns.seocrawler.repository.PostRepository;
import com.tns.seocrawler.service.dto.PostDTO;
import com.tns.seocrawler.service.mapper.PostMapper;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * Public read-only APIs for posts by tenant.
 */
@RestController
@RequestMapping("/api/public")
@Transactional(readOnly = true)
public class PublicPostResource {

    private final Logger log = LoggerFactory.getLogger(PublicPostResource.class);

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    public PublicPostResource(PostRepository postRepository, PostMapper postMapper) {
        this.postRepository = postRepository;
        this.postMapper = postMapper;
    }

    /**
     * GET /api/public/{tenantCode}/posts? page & size
     */
    @GetMapping("/{tenantCode}/posts")
    public ResponseEntity<List<PostDTO>> getPublicPosts(
        @PathVariable String tenantCode,
        @RequestParam(name = "page", defaultValue = "0") int page,
        @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        log.debug("REST request to get public posts for tenant {}", tenantCode);
        Pageable pageable = PageRequest.of(page, size);
        var postsPage = postRepository.findPublicByTenant(tenantCode, PostStatus.PUBLISHED, pageable);
        List<PostDTO> dtos = postsPage.getContent().stream().map(postMapper::toDto).toList();
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /api/public/{tenantCode}/posts/{slug}
     */
    @GetMapping("/{tenantCode}/posts/{slug}")
    public ResponseEntity<PostDTO> getPublicPost(@PathVariable String tenantCode, @PathVariable String slug) throws URISyntaxException {
        log.debug("REST request to get public post {} for tenant {}", slug, tenantCode);
        Optional<Post> postOpt = postRepository.findOnePublicByTenantAndSlug(tenantCode, slug, PostStatus.PUBLISHED);
        return postOpt.map(postMapper::toDto).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
