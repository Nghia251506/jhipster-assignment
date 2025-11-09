package com.tns.seocrawler.repository;

import com.tns.seocrawler.domain.Source;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Source entity.
 */
@Repository
public interface SourceRepository extends JpaRepository<Source, Long> {
    default Optional<Source> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<Source> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<Source> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select source from Source source left join fetch source.tenant left join fetch source.category",
        countQuery = "select count(source) from Source source"
    )
    Page<Source> findAllWithToOneRelationships(Pageable pageable);

    @Query("select source from Source source left join fetch source.tenant left join fetch source.category")
    List<Source> findAllWithToOneRelationships();

    @Query("select source from Source source left join fetch source.tenant left join fetch source.category where source.id =:id")
    Optional<Source> findOneWithToOneRelationships(@Param("id") Long id);

    @Query("select s from Source s where s.isActive = true and s.tenant.status = com.tns.seocrawler.domain.enumeration.TenantStatus.ACTIVE")
    List<Source> findAllActiveForActiveTenants();
}
