package com.tns.seocrawler.web.rest;

import static com.tns.seocrawler.domain.SourceAsserts.*;
import static com.tns.seocrawler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tns.seocrawler.IntegrationTest;
import com.tns.seocrawler.domain.Source;
import com.tns.seocrawler.repository.SourceRepository;
import com.tns.seocrawler.service.SourceService;
import com.tns.seocrawler.service.dto.SourceDTO;
import com.tns.seocrawler.service.mapper.SourceMapper;
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
 * Integration tests for the {@link SourceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class SourceResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_BASE_URL = "AAAAAAAAAA";
    private static final String UPDATED_BASE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LIST_URL = "AAAAAAAAAA";
    private static final String UPDATED_LIST_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LIST_ITEM_SELECTOR = "AAAAAAAAAA";
    private static final String UPDATED_LIST_ITEM_SELECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_LINK_ATTR = "AAAAAAAAAA";
    private static final String UPDATED_LINK_ATTR = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE_SELECTOR = "AAAAAAAAAA";
    private static final String UPDATED_TITLE_SELECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_SELECTOR = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_SELECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_SELECTOR = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_SELECTOR = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR_SELECTOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR_SELECTOR = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/sources";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SourceRepository sourceRepository;

    @Mock
    private SourceRepository sourceRepositoryMock;

    @Autowired
    private SourceMapper sourceMapper;

    @Mock
    private SourceService sourceServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSourceMockMvc;

    private Source source;

    private Source insertedSource;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Source createEntity() {
        return new Source()
            .name(DEFAULT_NAME)
            .baseUrl(DEFAULT_BASE_URL)
            .listUrl(DEFAULT_LIST_URL)
            .listItemSelector(DEFAULT_LIST_ITEM_SELECTOR)
            .linkAttr(DEFAULT_LINK_ATTR)
            .titleSelector(DEFAULT_TITLE_SELECTOR)
            .contentSelector(DEFAULT_CONTENT_SELECTOR)
            .thumbnailSelector(DEFAULT_THUMBNAIL_SELECTOR)
            .authorSelector(DEFAULT_AUTHOR_SELECTOR)
            .isActive(DEFAULT_IS_ACTIVE)
            .note(DEFAULT_NOTE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Source createUpdatedEntity() {
        return new Source()
            .name(UPDATED_NAME)
            .baseUrl(UPDATED_BASE_URL)
            .listUrl(UPDATED_LIST_URL)
            .listItemSelector(UPDATED_LIST_ITEM_SELECTOR)
            .linkAttr(UPDATED_LINK_ATTR)
            .titleSelector(UPDATED_TITLE_SELECTOR)
            .contentSelector(UPDATED_CONTENT_SELECTOR)
            .thumbnailSelector(UPDATED_THUMBNAIL_SELECTOR)
            .authorSelector(UPDATED_AUTHOR_SELECTOR)
            .isActive(UPDATED_IS_ACTIVE)
            .note(UPDATED_NOTE);
    }

    @BeforeEach
    void initTest() {
        source = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSource != null) {
            sourceRepository.delete(insertedSource);
            insertedSource = null;
        }
    }

    @Test
    @Transactional
    void createSource() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);
        var returnedSourceDTO = om.readValue(
            restSourceMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SourceDTO.class
        );

        // Validate the Source in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSource = sourceMapper.toEntity(returnedSourceDTO);
        assertSourceUpdatableFieldsEquals(returnedSource, getPersistedSource(returnedSource));

        insertedSource = returnedSource;
    }

    @Test
    @Transactional
    void createSourceWithExistingId() throws Exception {
        // Create the Source with an existing ID
        source.setId(1L);
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        source.setName(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBaseUrlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        source.setBaseUrl(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkListUrlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        source.setListUrl(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkListItemSelectorIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        source.setListItemSelector(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLinkAttrIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        source.setLinkAttr(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkIsActiveIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        source.setIsActive(null);

        // Create the Source, which fails.
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        restSourceMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllSources() throws Exception {
        // Initialize the database
        insertedSource = sourceRepository.saveAndFlush(source);

        // Get all the sourceList
        restSourceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(source.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].baseUrl").value(hasItem(DEFAULT_BASE_URL)))
            .andExpect(jsonPath("$.[*].listUrl").value(hasItem(DEFAULT_LIST_URL)))
            .andExpect(jsonPath("$.[*].listItemSelector").value(hasItem(DEFAULT_LIST_ITEM_SELECTOR)))
            .andExpect(jsonPath("$.[*].linkAttr").value(hasItem(DEFAULT_LINK_ATTR)))
            .andExpect(jsonPath("$.[*].titleSelector").value(hasItem(DEFAULT_TITLE_SELECTOR)))
            .andExpect(jsonPath("$.[*].contentSelector").value(hasItem(DEFAULT_CONTENT_SELECTOR)))
            .andExpect(jsonPath("$.[*].thumbnailSelector").value(hasItem(DEFAULT_THUMBNAIL_SELECTOR)))
            .andExpect(jsonPath("$.[*].authorSelector").value(hasItem(DEFAULT_AUTHOR_SELECTOR)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSourcesWithEagerRelationshipsIsEnabled() throws Exception {
        when(sourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSourceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(sourceServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllSourcesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(sourceServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restSourceMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(sourceRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getSource() throws Exception {
        // Initialize the database
        insertedSource = sourceRepository.saveAndFlush(source);

        // Get the source
        restSourceMockMvc
            .perform(get(ENTITY_API_URL_ID, source.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(source.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.baseUrl").value(DEFAULT_BASE_URL))
            .andExpect(jsonPath("$.listUrl").value(DEFAULT_LIST_URL))
            .andExpect(jsonPath("$.listItemSelector").value(DEFAULT_LIST_ITEM_SELECTOR))
            .andExpect(jsonPath("$.linkAttr").value(DEFAULT_LINK_ATTR))
            .andExpect(jsonPath("$.titleSelector").value(DEFAULT_TITLE_SELECTOR))
            .andExpect(jsonPath("$.contentSelector").value(DEFAULT_CONTENT_SELECTOR))
            .andExpect(jsonPath("$.thumbnailSelector").value(DEFAULT_THUMBNAIL_SELECTOR))
            .andExpect(jsonPath("$.authorSelector").value(DEFAULT_AUTHOR_SELECTOR))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE));
    }

    @Test
    @Transactional
    void getNonExistingSource() throws Exception {
        // Get the source
        restSourceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSource() throws Exception {
        // Initialize the database
        insertedSource = sourceRepository.saveAndFlush(source);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the source
        Source updatedSource = sourceRepository.findById(source.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSource are not directly saved in db
        em.detach(updatedSource);
        updatedSource
            .name(UPDATED_NAME)
            .baseUrl(UPDATED_BASE_URL)
            .listUrl(UPDATED_LIST_URL)
            .listItemSelector(UPDATED_LIST_ITEM_SELECTOR)
            .linkAttr(UPDATED_LINK_ATTR)
            .titleSelector(UPDATED_TITLE_SELECTOR)
            .contentSelector(UPDATED_CONTENT_SELECTOR)
            .thumbnailSelector(UPDATED_THUMBNAIL_SELECTOR)
            .authorSelector(UPDATED_AUTHOR_SELECTOR)
            .isActive(UPDATED_IS_ACTIVE)
            .note(UPDATED_NOTE);
        SourceDTO sourceDTO = sourceMapper.toDto(updatedSource);

        restSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sourceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO))
            )
            .andExpect(status().isOk());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSourceToMatchAllProperties(updatedSource);
    }

    @Test
    @Transactional
    void putNonExistingSource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        source.setId(longCount.incrementAndGet());

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, sourceDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        source.setId(longCount.incrementAndGet());

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(sourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        source.setId(longCount.incrementAndGet());

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSourceWithPatch() throws Exception {
        // Initialize the database
        insertedSource = sourceRepository.saveAndFlush(source);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the source using partial update
        Source partialUpdatedSource = new Source();
        partialUpdatedSource.setId(source.getId());

        partialUpdatedSource
            .baseUrl(UPDATED_BASE_URL)
            .listUrl(UPDATED_LIST_URL)
            .listItemSelector(UPDATED_LIST_ITEM_SELECTOR)
            .isActive(UPDATED_IS_ACTIVE)
            .note(UPDATED_NOTE);

        restSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSource))
            )
            .andExpect(status().isOk());

        // Validate the Source in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSourceUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSource, source), getPersistedSource(source));
    }

    @Test
    @Transactional
    void fullUpdateSourceWithPatch() throws Exception {
        // Initialize the database
        insertedSource = sourceRepository.saveAndFlush(source);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the source using partial update
        Source partialUpdatedSource = new Source();
        partialUpdatedSource.setId(source.getId());

        partialUpdatedSource
            .name(UPDATED_NAME)
            .baseUrl(UPDATED_BASE_URL)
            .listUrl(UPDATED_LIST_URL)
            .listItemSelector(UPDATED_LIST_ITEM_SELECTOR)
            .linkAttr(UPDATED_LINK_ATTR)
            .titleSelector(UPDATED_TITLE_SELECTOR)
            .contentSelector(UPDATED_CONTENT_SELECTOR)
            .thumbnailSelector(UPDATED_THUMBNAIL_SELECTOR)
            .authorSelector(UPDATED_AUTHOR_SELECTOR)
            .isActive(UPDATED_IS_ACTIVE)
            .note(UPDATED_NOTE);

        restSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSource.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSource))
            )
            .andExpect(status().isOk());

        // Validate the Source in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSourceUpdatableFieldsEquals(partialUpdatedSource, getPersistedSource(partialUpdatedSource));
    }

    @Test
    @Transactional
    void patchNonExistingSource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        source.setId(longCount.incrementAndGet());

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, sourceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        source.setId(longCount.incrementAndGet());

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(sourceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSource() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        source.setId(longCount.incrementAndGet());

        // Create the Source
        SourceDTO sourceDTO = sourceMapper.toDto(source);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSourceMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(sourceDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Source in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSource() throws Exception {
        // Initialize the database
        insertedSource = sourceRepository.saveAndFlush(source);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the source
        restSourceMockMvc
            .perform(delete(ENTITY_API_URL_ID, source.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return sourceRepository.count();
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

    protected Source getPersistedSource(Source source) {
        return sourceRepository.findById(source.getId()).orElseThrow();
    }

    protected void assertPersistedSourceToMatchAllProperties(Source expectedSource) {
        assertSourceAllPropertiesEquals(expectedSource, getPersistedSource(expectedSource));
    }

    protected void assertPersistedSourceToMatchUpdatableProperties(Source expectedSource) {
        assertSourceAllUpdatablePropertiesEquals(expectedSource, getPersistedSource(expectedSource));
    }
}
