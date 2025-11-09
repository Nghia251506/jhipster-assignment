package com.tns.seocrawler.repository;

import com.tns.seocrawler.domain.SiteSetting;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SiteSetting entity.
 */
@Repository
public interface SiteSettingRepository extends JpaRepository<SiteSetting, Long> {
    default Optional<SiteSetting> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SiteSetting> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SiteSetting> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select siteSetting from SiteSetting siteSetting left join fetch siteSetting.tenant",
        countQuery = "select count(siteSetting) from SiteSetting siteSetting"
    )
    Page<SiteSetting> findAllWithToOneRelationships(Pageable pageable);

    @Query("select siteSetting from SiteSetting siteSetting left join fetch siteSetting.tenant")
    List<SiteSetting> findAllWithToOneRelationships();

    @Query("select siteSetting from SiteSetting siteSetting left join fetch siteSetting.tenant where siteSetting.id =:id")
    Optional<SiteSetting> findOneWithToOneRelationships(@Param("id") Long id);
}
