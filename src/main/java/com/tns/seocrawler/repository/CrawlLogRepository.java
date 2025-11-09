package com.tns.seocrawler.repository;

import com.tns.seocrawler.domain.CrawlLog;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the CrawlLog entity.
 */
@Repository
public interface CrawlLogRepository extends JpaRepository<CrawlLog, Long> {
    default Optional<CrawlLog> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<CrawlLog> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<CrawlLog> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select crawlLog from CrawlLog crawlLog left join fetch crawlLog.tenant",
        countQuery = "select count(crawlLog) from CrawlLog crawlLog"
    )
    Page<CrawlLog> findAllWithToOneRelationships(Pageable pageable);

    @Query("select crawlLog from CrawlLog crawlLog left join fetch crawlLog.tenant")
    List<CrawlLog> findAllWithToOneRelationships();

    @Query("select crawlLog from CrawlLog crawlLog left join fetch crawlLog.tenant where crawlLog.id =:id")
    Optional<CrawlLog> findOneWithToOneRelationships(@Param("id") Long id);
}
