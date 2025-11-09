package com.tns.seocrawler.web.rest;

import static com.tns.seocrawler.domain.TenantAsserts.*;
import static com.tns.seocrawler.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tns.seocrawler.IntegrationTest;
import com.tns.seocrawler.domain.Tenant;
import com.tns.seocrawler.domain.enumeration.TenantStatus;
import com.tns.seocrawler.repository.TenantRepository;
import com.tns.seocrawler.service.dto.TenantDTO;
import com.tns.seocrawler.service.mapper.TenantMapper;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link TenantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class TenantResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTACT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_CONTACT_PHONE = "BBBBBBBBBB";

    private static final Integer DEFAULT_MAX_USERS = 1;
    private static final Integer UPDATED_MAX_USERS = 2;

    private static final TenantStatus DEFAULT_STATUS = TenantStatus.ACTIVE;
    private static final TenantStatus UPDATED_STATUS = TenantStatus.SUSPENDED;

    private static final String ENTITY_API_URL = "/api/tenants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TenantRepository tenantRepository;

    @Autowired
    private TenantMapper tenantMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restTenantMockMvc;

    private Tenant tenant;

    private Tenant insertedTenant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenant createEntity() {
        return new Tenant()
            .code(DEFAULT_CODE)
            .name(DEFAULT_NAME)
            .contactEmail(DEFAULT_CONTACT_EMAIL)
            .contactPhone(DEFAULT_CONTACT_PHONE)
            .maxUsers(DEFAULT_MAX_USERS)
            .status(DEFAULT_STATUS);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Tenant createUpdatedEntity() {
        return new Tenant()
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .maxUsers(UPDATED_MAX_USERS)
            .status(UPDATED_STATUS);
    }

    @BeforeEach
    void initTest() {
        tenant = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedTenant != null) {
            tenantRepository.delete(insertedTenant);
            insertedTenant = null;
        }
    }

    @Test
    @Transactional
    void createTenant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);
        var returnedTenantDTO = om.readValue(
            restTenantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            TenantDTO.class
        );

        // Validate the Tenant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedTenant = tenantMapper.toEntity(returnedTenantDTO);
        assertTenantUpdatableFieldsEquals(returnedTenant, getPersistedTenant(returnedTenant));

        insertedTenant = returnedTenant;
    }

    @Test
    @Transactional
    void createTenantWithExistingId() throws Exception {
        // Create the Tenant with an existing ID
        tenant.setId(1L);
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restTenantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkCodeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setCode(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setName(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkMaxUsersIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setMaxUsers(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkStatusIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        tenant.setStatus(null);

        // Create the Tenant, which fails.
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        restTenantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllTenants() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        // Get all the tenantList
        restTenantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tenant.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].contactEmail").value(hasItem(DEFAULT_CONTACT_EMAIL)))
            .andExpect(jsonPath("$.[*].contactPhone").value(hasItem(DEFAULT_CONTACT_PHONE)))
            .andExpect(jsonPath("$.[*].maxUsers").value(hasItem(DEFAULT_MAX_USERS)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getTenant() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        // Get the tenant
        restTenantMockMvc
            .perform(get(ENTITY_API_URL_ID, tenant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(tenant.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.contactEmail").value(DEFAULT_CONTACT_EMAIL))
            .andExpect(jsonPath("$.contactPhone").value(DEFAULT_CONTACT_PHONE))
            .andExpect(jsonPath("$.maxUsers").value(DEFAULT_MAX_USERS))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingTenant() throws Exception {
        // Get the tenant
        restTenantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingTenant() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenant
        Tenant updatedTenant = tenantRepository.findById(tenant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedTenant are not directly saved in db
        em.detach(updatedTenant);
        updatedTenant
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .maxUsers(UPDATED_MAX_USERS)
            .status(UPDATED_STATUS);
        TenantDTO tenantDTO = tenantMapper.toDto(updatedTenant);

        restTenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenantDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isOk());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedTenantToMatchAllProperties(updatedTenant);
    }

    @Test
    @Transactional
    void putNonExistingTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, tenantDTO.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateTenantWithPatch() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenant using partial update
        Tenant partialUpdatedTenant = new Tenant();
        partialUpdatedTenant.setId(tenant.getId());

        partialUpdatedTenant.contactEmail(UPDATED_CONTACT_EMAIL);

        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenant))
            )
            .andExpect(status().isOk());

        // Validate the Tenant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenantUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedTenant, tenant), getPersistedTenant(tenant));
    }

    @Test
    @Transactional
    void fullUpdateTenantWithPatch() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the tenant using partial update
        Tenant partialUpdatedTenant = new Tenant();
        partialUpdatedTenant.setId(tenant.getId());

        partialUpdatedTenant
            .code(UPDATED_CODE)
            .name(UPDATED_NAME)
            .contactEmail(UPDATED_CONTACT_EMAIL)
            .contactPhone(UPDATED_CONTACT_PHONE)
            .maxUsers(UPDATED_MAX_USERS)
            .status(UPDATED_STATUS);

        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedTenant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedTenant))
            )
            .andExpect(status().isOk());

        // Validate the Tenant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertTenantUpdatableFieldsEquals(partialUpdatedTenant, getPersistedTenant(partialUpdatedTenant));
    }

    @Test
    @Transactional
    void patchNonExistingTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, tenantDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(tenantDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamTenant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        tenant.setId(longCount.incrementAndGet());

        // Create the Tenant
        TenantDTO tenantDTO = tenantMapper.toDto(tenant);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restTenantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(tenantDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Tenant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteTenant() throws Exception {
        // Initialize the database
        insertedTenant = tenantRepository.saveAndFlush(tenant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the tenant
        restTenantMockMvc
            .perform(delete(ENTITY_API_URL_ID, tenant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return tenantRepository.count();
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

    protected Tenant getPersistedTenant(Tenant tenant) {
        return tenantRepository.findById(tenant.getId()).orElseThrow();
    }

    protected void assertPersistedTenantToMatchAllProperties(Tenant expectedTenant) {
        assertTenantAllPropertiesEquals(expectedTenant, getPersistedTenant(expectedTenant));
    }

    protected void assertPersistedTenantToMatchUpdatableProperties(Tenant expectedTenant) {
        assertTenantAllUpdatablePropertiesEquals(expectedTenant, getPersistedTenant(expectedTenant));
    }
}
