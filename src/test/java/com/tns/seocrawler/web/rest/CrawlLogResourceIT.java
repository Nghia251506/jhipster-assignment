package com.tns.seocrawler.web.rest;

import static com.tns.seocrawler.domain.CrawlLogAsserts.*;
import static com.tns.seocrawler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tns.seocrawler.IntegrationTest;
import com.tns.seocrawler.domain.CrawlLog;
import com.tns.seocrawler.domain.enumeration.CrawlStatus;
import com.tns.seocrawler.repository.CrawlLogRepository;
import com.tns.seocrawler.service.CrawlLogService;
import com.tns.seocrawler.service.dto.CrawlLogDTO;
import com.tns.seocrawler.service.mapper.CrawlLogMapper;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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
 * Integration tests for the {@link CrawlLogResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class CrawlLogResourceIT {

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    private static final CrawlStatus DEFAULT_STATUS = CrawlStatus.NEW;
    private static final CrawlStatus UPDATED_STATUS = CrawlStatus.SKIPPED;

    private static final String DEFAULT_ERROR_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_ERROR_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CRAWLED_AT = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CRAWLED_AT = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/crawl-logs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private CrawlLogRepository crawlLogRepository;

    @Mock
    private CrawlLogRepository crawlLogRepositoryMock;

    @Autowired
    private CrawlLogMapper crawlLogMapper;

    @Mock
    private CrawlLogService crawlLogServiceMock;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restCrawlLogMockMvc;

    private CrawlLog crawlLog;

    private CrawlLog insertedCrawlLog;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrawlLog createEntity() {
        return new CrawlLog().url(DEFAULT_URL).status(DEFAULT_STATUS).errorMessage(DEFAULT_ERROR_MESSAGE).crawledAt(DEFAULT_CRAWLED_AT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CrawlLog createUpdatedEntity() {
        return new CrawlLog().url(UPDATED_URL).status(UPDATED_STATUS).errorMessage(UPDATED_ERROR_MESSAGE).crawledAt(UPDATED_CRAWLED_AT);
    }

    @BeforeEach
    void initTest() {
        crawlLog = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedCrawlLog != null) {
            crawlLogRepository.delete(insertedCrawlLog);
            insertedCrawlLog = null;
        }
    }

    @Test
    @Transactional
    void createCrawlLog() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the CrawlLog
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);
        var returnedCrawlLogDTO = om.readValue(
            restCrawlLogMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlLogDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            CrawlLogDTO.class
        );

        // Validate the CrawlLog in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedCrawlLog = crawlLogMapper.toEntity(returnedCrawlLogDTO);
        assertCrawlLogUpdatableFieldsEquals(returnedCrawlLog, getPersistedCrawlLog(returnedCrawlLog));

        insertedCrawlLog = returnedCrawlLog;
    }

    @Test
    @Transactional
    void createCrawlLogWithExistingId() throws Exception {
        // Create the CrawlLog with an existing ID
        crawlLog.setId(1L);
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restCrawlLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlLogDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUrlIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crawlLog.setUrl(null);

        // Create the CrawlLog, which fails.
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        restCrawlLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlLogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crawlLog.setStatus(null);

        // Create the CrawlLog, which fails.
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        restCrawlLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlLogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkCrawledAtIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        crawlLog.setCrawledAt(null);

        // Create the CrawlLog, which fails.
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        restCrawlLogMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlLogDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllCrawlLogs() throws Exception {
        // Initialize the database
        insertedCrawlLog = crawlLogRepository.saveAndFlush(crawlLog);

        // Get all the crawlLogList
        restCrawlLogMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(crawlLog.getId().intValue())))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].errorMessage").value(hasItem(DEFAULT_ERROR_MESSAGE)))
            .andExpect(jsonPath("$.[*].crawledAt").value(hasItem(DEFAULT_CRAWLED_AT.toString())));
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCrawlLogsWithEagerRelationshipsIsEnabled() throws Exception {
        when(crawlLogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCrawlLogMockMvc.perform(get(ENTITY_API_URL + "?eagerload=true")).andExpect(status().isOk());

        verify(crawlLogServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({ "unchecked" })
    void getAllCrawlLogsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(crawlLogServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restCrawlLogMockMvc.perform(get(ENTITY_API_URL + "?eagerload=false")).andExpect(status().isOk());
        verify(crawlLogRepositoryMock, times(1)).findAll(any(Pageable.class));
    }

    @Test
    @Transactional
    void getCrawlLog() throws Exception {
        // Initialize the database
        insertedCrawlLog = crawlLogRepository.saveAndFlush(crawlLog);

        // Get the crawlLog
        restCrawlLogMockMvc
            .perform(get(ENTITY_API_URL_ID, crawlLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(crawlLog.getId().intValue()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.errorMessage").value(DEFAULT_ERROR_MESSAGE))
            .andExpect(jsonPath("$.crawledAt").value(DEFAULT_CRAWLED_AT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingCrawlLog() throws Exception {
        // Get the crawlLog
        restCrawlLogMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingCrawlLog() throws Exception {
        // Initialize the database
        insertedCrawlLog = crawlLogRepository.saveAndFlush(crawlLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crawlLog
        CrawlLog updatedCrawlLog = crawlLogRepository.findById(crawlLog.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedCrawlLog are not directly saved in db
        em.detach(updatedCrawlLog);
        updatedCrawlLog.url(UPDATED_URL).status(UPDATED_STATUS).errorMessage(UPDATED_ERROR_MESSAGE).crawledAt(UPDATED_CRAWLED_AT);
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(updatedCrawlLog);

        restCrawlLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crawlLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(crawlLogDTO))
            )
            .andExpect(status().isOk());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedCrawlLogToMatchAllProperties(updatedCrawlLog);
    }

    @Test
    @Transactional
    void putNonExistingCrawlLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawlLog.setId(longCount.incrementAndGet());

        // Create the CrawlLog
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrawlLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, crawlLogDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(crawlLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchCrawlLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawlLog.setId(longCount.incrementAndGet());

        // Create the CrawlLog
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlLogMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(crawlLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamCrawlLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawlLog.setId(longCount.incrementAndGet());

        // Create the CrawlLog
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlLogMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(crawlLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateCrawlLogWithPatch() throws Exception {
        // Initialize the database
        insertedCrawlLog = crawlLogRepository.saveAndFlush(crawlLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crawlLog using partial update
        CrawlLog partialUpdatedCrawlLog = new CrawlLog();
        partialUpdatedCrawlLog.setId(crawlLog.getId());

        partialUpdatedCrawlLog.status(UPDATED_STATUS).crawledAt(UPDATED_CRAWLED_AT);

        restCrawlLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrawlLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrawlLog))
            )
            .andExpect(status().isOk());

        // Validate the CrawlLog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCrawlLogUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedCrawlLog, crawlLog), getPersistedCrawlLog(crawlLog));
    }

    @Test
    @Transactional
    void fullUpdateCrawlLogWithPatch() throws Exception {
        // Initialize the database
        insertedCrawlLog = crawlLogRepository.saveAndFlush(crawlLog);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the crawlLog using partial update
        CrawlLog partialUpdatedCrawlLog = new CrawlLog();
        partialUpdatedCrawlLog.setId(crawlLog.getId());

        partialUpdatedCrawlLog.url(UPDATED_URL).status(UPDATED_STATUS).errorMessage(UPDATED_ERROR_MESSAGE).crawledAt(UPDATED_CRAWLED_AT);

        restCrawlLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedCrawlLog.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedCrawlLog))
            )
            .andExpect(status().isOk());

        // Validate the CrawlLog in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertCrawlLogUpdatableFieldsEquals(partialUpdatedCrawlLog, getPersistedCrawlLog(partialUpdatedCrawlLog));
    }

    @Test
    @Transactional
    void patchNonExistingCrawlLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawlLog.setId(longCount.incrementAndGet());

        // Create the CrawlLog
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCrawlLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, crawlLogDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(crawlLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchCrawlLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawlLog.setId(longCount.incrementAndGet());

        // Create the CrawlLog
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlLogMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(crawlLogDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamCrawlLog() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        crawlLog.setId(longCount.incrementAndGet());

        // Create the CrawlLog
        CrawlLogDTO crawlLogDTO = crawlLogMapper.toDto(crawlLog);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restCrawlLogMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(crawlLogDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the CrawlLog in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteCrawlLog() throws Exception {
        // Initialize the database
        insertedCrawlLog = crawlLogRepository.saveAndFlush(crawlLog);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the crawlLog
        restCrawlLogMockMvc
            .perform(delete(ENTITY_API_URL_ID, crawlLog.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return crawlLogRepository.count();
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

    protected CrawlLog getPersistedCrawlLog(CrawlLog crawlLog) {
        return crawlLogRepository.findById(crawlLog.getId()).orElseThrow();
    }

    protected void assertPersistedCrawlLogToMatchAllProperties(CrawlLog expectedCrawlLog) {
        assertCrawlLogAllPropertiesEquals(expectedCrawlLog, getPersistedCrawlLog(expectedCrawlLog));
    }

    protected void assertPersistedCrawlLogToMatchUpdatableProperties(CrawlLog expectedCrawlLog) {
        assertCrawlLogAllUpdatablePropertiesEquals(expectedCrawlLog, getPersistedCrawlLog(expectedCrawlLog));
    }
}
