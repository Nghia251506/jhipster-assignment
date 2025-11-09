package com.tns.seocrawler.service;

import com.tns.seocrawler.domain.SiteSetting;
import com.tns.seocrawler.repository.SiteSettingRepository;
import com.tns.seocrawler.service.dto.SiteSettingDTO;
import com.tns.seocrawler.service.mapper.SiteSettingMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.tns.seocrawler.domain.SiteSetting}.
 */
@Service
@Transactional
public class SiteSettingService {

    private static final Logger LOG = LoggerFactory.getLogger(SiteSettingService.class);

    private final SiteSettingRepository siteSettingRepository;

    private final SiteSettingMapper siteSettingMapper;

    public SiteSettingService(SiteSettingRepository siteSettingRepository, SiteSettingMapper siteSettingMapper) {
        this.siteSettingRepository = siteSettingRepository;
        this.siteSettingMapper = siteSettingMapper;
    }

    /**
     * Save a siteSetting.
     *
     * @param siteSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteSettingDTO save(SiteSettingDTO siteSettingDTO) {
        LOG.debug("Request to save SiteSetting : {}", siteSettingDTO);
        SiteSetting siteSetting = siteSettingMapper.toEntity(siteSettingDTO);
        siteSetting = siteSettingRepository.save(siteSetting);
        return siteSettingMapper.toDto(siteSetting);
    }

    /**
     * Update a siteSetting.
     *
     * @param siteSettingDTO the entity to save.
     * @return the persisted entity.
     */
    public SiteSettingDTO update(SiteSettingDTO siteSettingDTO) {
        LOG.debug("Request to update SiteSetting : {}", siteSettingDTO);
        SiteSetting siteSetting = siteSettingMapper.toEntity(siteSettingDTO);
        siteSetting = siteSettingRepository.save(siteSetting);
        return siteSettingMapper.toDto(siteSetting);
    }

    /**
     * Partially update a siteSetting.
     *
     * @param siteSettingDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<SiteSettingDTO> partialUpdate(SiteSettingDTO siteSettingDTO) {
        LOG.debug("Request to partially update SiteSetting : {}", siteSettingDTO);

        return siteSettingRepository
            .findById(siteSettingDTO.getId())
            .map(existingSiteSetting -> {
                siteSettingMapper.partialUpdate(existingSiteSetting, siteSettingDTO);

                return existingSiteSetting;
            })
            .map(siteSettingRepository::save)
            .map(siteSettingMapper::toDto);
    }

    /**
     * Get all the siteSettings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SiteSettingDTO> findAll(Pageable pageable) {
        LOG.debug("Request to get all SiteSettings");
        return siteSettingRepository.findAll(pageable).map(siteSettingMapper::toDto);
    }

    /**
     * Get all the siteSettings with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<SiteSettingDTO> findAllWithEagerRelationships(Pageable pageable) {
        return siteSettingRepository.findAllWithEagerRelationships(pageable).map(siteSettingMapper::toDto);
    }

    /**
     * Get one siteSetting by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SiteSettingDTO> findOne(Long id) {
        LOG.debug("Request to get SiteSetting : {}", id);
        return siteSettingRepository.findOneWithEagerRelationships(id).map(siteSettingMapper::toDto);
    }

    /**
     * Delete the siteSetting by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        LOG.debug("Request to delete SiteSetting : {}", id);
        siteSettingRepository.deleteById(id);
    }
}
