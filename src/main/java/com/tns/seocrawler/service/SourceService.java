package com.tns.seocrawler.service;

import com.tns.seocrawler.domain.Source;
import com.tns.seocrawler.repository.SourceRepository;
import com.tns.seocrawler.service.dto.SourceDTO;
import com.tns.seocrawler.service.mapper.SourceMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tns.seocrawler.domain.Source}.
 */
@Service
@Transactional
public class SourceService {

    private static final Logger LOG = LoggerFactory.getLogger(SourceService.class);

    private final SourceRepository sourceRepository;

    private final SourceMapper sourceMapper;

    public SourceService(SourceRepository sourceRepository, SourceMapper sourceMapper) {
        this.sourceRepository = sourceRepository;
        this.sourceMapper = sourceMapper;
    }

    /**
     * Save a source.
     *
     * @param sourceDTO the entity to save.
     * @return the persisted entity.
     */
    public SourceDTO save(SourceDTO sourceDTO) {
        LOG.debug("Request to save Source : {}", sourceDTO);
        Source source = sourceMapper.toEntity(sourceDTO);
        source = sourceRepository.save(source);
        return sourceMapper.toDto(source);
    }

    /**
     * Update a source.
     *
     * @param sourceDTO the entity to save.
     * @return the persisted entity.
     */
    public SourceDTO update(SourceDTO sourceDTO) {
        LOG.debug("Request to update Source : {}", sourceDTO);
        Source source = sourceMapper.toEntity(sourceDTO);
        source = sourceRepository.save(source);
        return sourceMapper.toDto(source);
    }

    /**
     * Partially update a source.
     *
     * @param sourceDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SourceDTO> partialUpdate(SourceDTO sourceDTO) {
        LOG.debug("Request to partially update Source : {}", sourceDTO);

        return sourceRepository
            .findById(sourceDTO.getId())
            .map(existingSource -> {
                sourceMapper.partialUpdate(existingSource, sourceDTO);

                return existingSource;
            })
            .map(sourceRepository::save)
            .map(sourceMapper::toDto);
    }

    /**
     * Get all the sources.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SourceDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all Sources");
        return sourceRepository.findAll(pageable).map(sourceMapper::toDto);
    }

    /**
     * Get all the sources with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SourceDTO> findAllWithEagerRelationships(Pageable pageable) {
        return sourceRepository.findAllWithEagerRelationships(pageable).map(sourceMapper::toDto);
    }

    /**
     * Get one source by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SourceDTO> findOne(Long id) {
        LOG.debug("Request to get Source : {}", id);
        return sourceRepository.findOneWithEagerRelationships(id).map(sourceMapper::toDto);
    }

    /**
     * Delete the source by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete Source : {}", id);
        sourceRepository.deleteById(id);
    }
}
