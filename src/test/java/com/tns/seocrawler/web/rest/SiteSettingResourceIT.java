package com.tns.seocrawler.web.rest;

import static com.tns.seocrawler.domain.SiteSettingAsserts.*;
import static com.tns.seocrawler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tns.seocrawler.IntegrationTest;
import com.tns.seocrawler.domain.SiteSetting;
import com.tns.seocrawler.repository.SiteSettingRepository;
import com.tns.seocrawler.service.SiteSettingService;
import com.tns.seocrawler.service.dto.SiteSettingDTO;
import com.tns.seocrawler.service.mapper.SiteSettingMapper;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SiteSettingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SiteSettingResourceIT {

    private static final String DEFAULT_SITE_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_SITE_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_SITE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_KEYWORDS = "AAAAAAAAAA";
    private static final String UPDATED_SITE_KEYWORDS = "BBBBBBBBBB";

    private static final String DEFAULT_GA_TRACKING_ID = "AAAAAAAAAA";
    private static final String UPDATED_GA_TRACKING_ID = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_TOP = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_TOP = "BBBBBBBBBB";

    private static final String DEFAULT_BANNER_SIDEBAR = "AAAAAAAAAA";
    private static final String UPDATED_BANNER_SIDEBAR = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/site-settings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SiteSettingRepository siteSettingRepository;

    @Mock
    private SiteSettingRepository siteSettingRepositoryMock;

    @Autowired
    private SiteSettingMapper siteSettingMapper;

    @Mock
    private SiteSettingService siteSettingServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteSettingMockMvc;

    private SiteSetting siteSetting;

    private SiteSetting insertedSiteSetting;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSetting createEntity() {
        return new SiteSetting()
            .siteTitle(DEFAULT_SITE_TITLE)
            .siteDescription(DEFAULT_SITE_DESCRIPTION)
            .siteKeywords(DEFAULT_SITE_KEYWORDS)
            .gaTrackingId(DEFAULT_GA_TRACKING_ID)
            .bannerTop(DEFAULT_BANNER_TOP)
            .bannerSidebar(DEFAULT_BANNER_SIDEBAR);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteSetting createUpdatedEntity() {
        return new SiteSetting()
            .siteTitle(UPDATED_SITE_TITLE)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .siteKeywords(UPDATED_SITE_KEYWORDS)
            .gaTrackingId(UPDATED_GA_TRACKING_ID)
            .bannerTop(UPDATED_BANNER_TOP)
            .bannerSidebar(UPDATED_BANNER_SIDEBAR);
    }

    @BeforeEach
    void initTest() {
        siteSetting = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSiteSetting != null) {
            siteSettingRepository.delete(insertedSiteSetting);
            insertedSiteSetting = null;
        }
    }

    @Test
    @Transactional
    void createSiteSetting() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SiteSetting
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);
        var returnedSiteSettingDTO = om.readValue(
            restSiteSettingMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteSettingDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SiteSettingDTO.class
        );

        // Validate the SiteSetting in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSiteSetting = siteSettingMapper.toEntity(returnedSiteSettingDTO);
        assertSiteSettingUpdatableFieldsEquals(returnedSiteSetting, getPersistedSiteSetting(returnedSiteSetting));

        insertedSiteSetting = returnedSiteSetting;
    }

    @Test
    @Transactional
    void createSiteSettingWithExistingId() throws Exception {
        // Create the SiteSetting with an existing ID
        siteSetting.setId(1L);
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteSettingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteSettingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSiteSettings() throws Exception {
        // Initialize the database
        insertedSiteSetting = siteSettingRepository.saveAndFlush(siteSetting);

        // Get all the siteSettingList
        restSiteSettingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteSetting.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteTitle").value(hasItem(DEFAULT_SITE_TITLE)))
            .andExpect(jsonPath("$.[*].siteDescription").value(hasItem(DEFAULT_SITE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].siteKeywords").value(hasItem(DEFAULT_SITE_KEYWORDS)))
            .andExpect(jsonPath("$.[*].gaTrackingId").value(hasItem(DEFAULT_GA_TRACKING_ID)))
            .andExpect(jsonPath("$.[*].bannerTop").value(hasItem(DEFAULT_BANNER_TOP)))
            .andExpect(jsonPath("$.[*].bannerSidebar").value(hasItem(DEFAULT_BANNER_SIDEBAR)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSiteSettingsWithEagerRelationshipsIsEnabled() throws Exception {
        when(siteSettingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSiteSettingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(siteSettingServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSiteSettingsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(siteSettingServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSiteSettingMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(siteSettingRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSiteSetting() throws Exception {
        // Initialize the database
        insertedSiteSetting = siteSettingRepository.saveAndFlush(siteSetting);

        // Get the siteSetting
        restSiteSettingMockMvc
            .perform(get(ENTITY_API_URL_ID, siteSetting.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteSetting.getId().intValue()))
            .andExpect(jsonPath("$.siteTitle").value(DEFAULT_SITE_TITLE))
            .andExpect(jsonPath("$.siteDescription").value(DEFAULT_SITE_DESCRIPTION))
            .andExpect(jsonPath("$.siteKeywords").value(DEFAULT_SITE_KEYWORDS))
            .andExpect(jsonPath("$.gaTrackingId").value(DEFAULT_GA_TRACKING_ID))
            .andExpect(jsonPath("$.bannerTop").value(DEFAULT_BANNER_TOP))
            .andExpect(jsonPath("$.bannerSidebar").value(DEFAULT_BANNER_SIDEBAR));
    }

    @Test
    @Transactional
    void getNonExistingSiteSetting() throws Exception {
        // Get the siteSetting
        restSiteSettingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSiteSetting() throws Exception {
        // Initialize the database
        insertedSiteSetting = siteSettingRepository.saveAndFlush(siteSetting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the siteSetting
        SiteSetting updatedSiteSetting = siteSettingRepository.findById(siteSetting.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSiteSetting are not directly saved in db
        em.detach(updatedSiteSetting);
        updatedSiteSetting
            .siteTitle(UPDATED_SITE_TITLE)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .siteKeywords(UPDATED_SITE_KEYWORDS)
            .gaTrackingId(UPDATED_GA_TRACKING_ID)
            .bannerTop(UPDATED_BANNER_TOP)
            .bannerSidebar(UPDATED_BANNER_SIDEBAR);
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(updatedSiteSetting);

        restSiteSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(siteSettingDTO))
            )
            .andExpect(status().isOk());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSiteSettingToMatchAllProperties(updatedSiteSetting);
    }

    @Test
    @Transactional
    void putNonExistingSiteSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteSetting.setId(longCount.incrementAndGet());

        // Create the SiteSetting
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteSettingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(siteSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteSetting.setId(longCount.incrementAndGet());

        // Create the SiteSetting
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSettingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(siteSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteSetting.setId(longCount.incrementAndGet());

        // Create the SiteSetting
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSettingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteSettingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteSettingWithPatch() throws Exception {
        // Initialize the database
        insertedSiteSetting = siteSettingRepository.saveAndFlush(siteSetting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the siteSetting using partial update
        SiteSetting partialUpdatedSiteSetting = new SiteSetting();
        partialUpdatedSiteSetting.setId(siteSetting.getId());

        partialUpdatedSiteSetting.siteTitle(UPDATED_SITE_TITLE).gaTrackingId(UPDATED_GA_TRACKING_ID).bannerSidebar(UPDATED_BANNER_SIDEBAR);

        restSiteSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSiteSetting))
            )
            .andExpect(status().isOk());

        // Validate the SiteSetting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteSettingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSiteSetting, siteSetting),
            getPersistedSiteSetting(siteSetting)
        );
    }

    @Test
    @Transactional
    void fullUpdateSiteSettingWithPatch() throws Exception {
        // Initialize the database
        insertedSiteSetting = siteSettingRepository.saveAndFlush(siteSetting);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the siteSetting using partial update
        SiteSetting partialUpdatedSiteSetting = new SiteSetting();
        partialUpdatedSiteSetting.setId(siteSetting.getId());

        partialUpdatedSiteSetting
            .siteTitle(UPDATED_SITE_TITLE)
            .siteDescription(UPDATED_SITE_DESCRIPTION)
            .siteKeywords(UPDATED_SITE_KEYWORDS)
            .gaTrackingId(UPDATED_GA_TRACKING_ID)
            .bannerTop(UPDATED_BANNER_TOP)
            .bannerSidebar(UPDATED_BANNER_SIDEBAR);

        restSiteSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteSetting.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSiteSetting))
            )
            .andExpect(status().isOk());

        // Validate the SiteSetting in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteSettingUpdatableFieldsEquals(partialUpdatedSiteSetting, getPersistedSiteSetting(partialUpdatedSiteSetting));
    }

    @Test
    @Transactional
    void patchNonExistingSiteSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteSetting.setId(longCount.incrementAndGet());

        // Create the SiteSetting
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteSettingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(siteSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteSetting.setId(longCount.incrementAndGet());

        // Create the SiteSetting
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSettingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(siteSettingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteSetting() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteSetting.setId(longCount.incrementAndGet());

        // Create the SiteSetting
        SiteSettingDTO siteSettingDTO = siteSettingMapper.toDto(siteSetting);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteSettingMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(siteSettingDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteSetting in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteSetting() throws Exception {
        // Initialize the database
        insertedSiteSetting = siteSettingRepository.saveAndFlush(siteSetting);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the siteSetting
        restSiteSettingMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteSetting.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return siteSettingRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected SiteSetting getPersistedSiteSetting(SiteSetting siteSetting) {
        return siteSettingRepository.findById(siteSetting.getId()).orElseThrow();
    }

    protected void assertPersistedSiteSettingToMatchAllProperties(SiteSetting expectedSiteSetting) {
        assertSiteSettingAllPropertiesEquals(expectedSiteSetting, getPersistedSiteSetting(expectedSiteSetting));
    }

    protected void assertPersistedSiteSettingToMatchUpdatableProperties(SiteSetting expectedSiteSetting) {
        assertSiteSettingAllUpdatablePropertiesEquals(expectedSiteSetting, getPersistedSiteSetting(expectedSiteSetting));
    }
}
