package com.tns.seocrawler.service;

import com.tns.seocrawler.domain.CrawlLog;
import com.tns.seocrawler.repository.CrawlLogRepository;
import com.tns.seocrawler.service.dto.CrawlLogDTO;
import com.tns.seocrawler.service.mapper.CrawlLogMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tns.seocrawler.domain.CrawlLog}.
 */
@Service
@Transactional
public class CrawlLogService {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlLogService.class);

    private final CrawlLogRepository crawlLogRepository;

    private final CrawlLogMapper crawlLogMapper;

    public CrawlLogService(CrawlLogRepository crawlLogRepository, CrawlLogMapper crawlLogMapper) {
        this.crawlLogRepository = crawlLogRepository;
        this.crawlLogMapper = crawlLogMapper;
    }

    /**
     * Save a crawlLog.
     *
     * @param crawlLogDTO the entity to save.
     * @return the persisted entity.
     */
    public CrawlLogDTO save(CrawlLogDTO crawlLogDTO) {
        LOG.debug("Request to save CrawlLog : {}", crawlLogDTO);
        CrawlLog crawlLog = crawlLogMapper.toEntity(crawlLogDTO);
        crawlLog = crawlLogRepository.save(crawlLog);
        return crawlLogMapper.toDto(crawlLog);
    }

    /**
     * Update a crawlLog.
     *
     * @param crawlLogDTO the entity to save.
     * @return the persisted entity.
     */
    public CrawlLogDTO update(CrawlLogDTO crawlLogDTO) {
        LOG.debug("Request to update CrawlLog : {}", crawlLogDTO);
        CrawlLog crawlLog = crawlLogMapper.toEntity(crawlLogDTO);
        crawlLog = crawlLogRepository.save(crawlLog);
        return crawlLogMapper.toDto(crawlLog);
    }

    /**
     * Partially update a crawlLog.
     *
     * @param crawlLogDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<CrawlLogDTO> partialUpdate(CrawlLogDTO crawlLogDTO) {
        LOG.debug("Request to partially update CrawlLog : {}", crawlLogDTO);

        return crawlLogRepository
            .findById(crawlLogDTO.getId())
            .map(existingCrawlLog -> {
                crawlLogMapper.partialUpdate(existingCrawlLog, crawlLogDTO);

                return existingCrawlLog;
            })
            .map(crawlLogRepository::save)
            .map(crawlLogMapper::toDto);
    }

    /**
     * Get all the crawlLogs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CrawlLogDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all CrawlLogs");
        return crawlLogRepository.findAll(pageable).map(crawlLogMapper::toDto);
    }

    /**
     * Get all the crawlLogs with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<CrawlLogDTO> findAllWithEagerRelationships(Pageable pageable) {
        return crawlLogRepository.findAllWithEagerRelationships(pageable).map(crawlLogMapper::toDto);
    }

    /**
     * Get one crawlLog by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CrawlLogDTO> findOne(Long id) {
        LOG.debug("Request to get CrawlLog : {}", id);
        return crawlLogRepository.findOneWithEagerRelationships(id).map(crawlLogMapper::toDto);
    }

    /**
     * Delete the crawlLog by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete CrawlLog : {}", id);
        crawlLogRepository.deleteById(id);
    }
}
