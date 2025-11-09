package com.tns.seocrawler.web.rest;

import com.tns.seocrawler.repository.CrawlLogRepository;
import com.tns.seocrawler.service.CrawlLogService;
import com.tns.seocrawler.service.dto.CrawlLogDTO;
import com.tns.seocrawler.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.tns.seocrawler.domain.CrawlLog}.
 */
@RestController
@RequestMapping("/api/crawl-logs")
public class CrawlLogResource {

    private static final Logger LOG = LoggerFactory.getLogger(CrawlLogResource.class);

    private static final String ENTITY_NAME = "crawlLog";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CrawlLogService crawlLogService;

    private final CrawlLogRepository crawlLogRepository;

    public CrawlLogResource(CrawlLogService crawlLogService, CrawlLogRepository crawlLogRepository) {
        this.crawlLogService = crawlLogService;
        this.crawlLogRepository = crawlLogRepository;
    }

    /**
     * {@code POST  /crawl-logs} : Create a new crawlLog.
     *
     * @param crawlLogDTO the crawlLogDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new crawlLogDTO, or with status {@code 400 (Bad Request)} if the crawlLog has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CrawlLogDTO> createCrawlLog(@Valid @RequestBody CrawlLogDTO crawlLogDTO) throws URISyntaxException {
        LOG.debug("REST request to save CrawlLog : {}", crawlLogDTO);
        if (crawlLogDTO.getId() != null) {
            throw new BadRequestAlertException("A new crawlLog cannot already have an ID", ENTITY_NAME, "idexists");
        }
        crawlLogDTO = crawlLogService.save(crawlLogDTO);
        return ResponseEntity.created(new URI("/api/crawl-logs/" + crawlLogDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, crawlLogDTO.getId().toString()))
            .body(crawlLogDTO);
    }

    /**
     * {@code PUT  /crawl-logs/:id} : Updates an existing crawlLog.
     *
     * @param id the id of the crawlLogDTO to save.
     * @param crawlLogDTO the crawlLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlLogDTO,
     * or with status {@code 400 (Bad Request)} if the crawlLogDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the crawlLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CrawlLogDTO> updateCrawlLog(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody CrawlLogDTO crawlLogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update CrawlLog : {}, {}", id, crawlLogDTO);
        if (crawlLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        crawlLogDTO = crawlLogService.update(crawlLogDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crawlLogDTO.getId().toString()))
            .body(crawlLogDTO);
    }

    /**
     * {@code PATCH  /crawl-logs/:id} : Partial updates given fields of an existing crawlLog, field will ignore if it is null
     *
     * @param id the id of the crawlLogDTO to save.
     * @param crawlLogDTO the crawlLogDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated crawlLogDTO,
     * or with status {@code 400 (Bad Request)} if the crawlLogDTO is not valid,
     * or with status {@code 404 (Not Found)} if the crawlLogDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the crawlLogDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CrawlLogDTO> partialUpdateCrawlLog(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody CrawlLogDTO crawlLogDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update CrawlLog partially : {}, {}", id, crawlLogDTO);
        if (crawlLogDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, crawlLogDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!crawlLogRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<CrawlLogDTO> result = crawlLogService.partialUpdate(crawlLogDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, crawlLogDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /crawl-logs} : get all the crawlLogs.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of crawlLogs in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CrawlLogDTO>> getAllCrawlLogs(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable,
        @RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload
    ) {
        LOG.debug("REST request to get a page of CrawlLogs");
        Page<CrawlLogDTO> page;
        if (eagerload) {
            page = crawlLogService.findAllWithEagerRelationships(pageable);
        } else {
            page = crawlLogService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /crawl-logs/:id} : get the "id" crawlLog.
     *
     * @param id the id of the crawlLogDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the crawlLogDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CrawlLogDTO> getCrawlLog(@PathVariable("id") Long id) {
        LOG.debug("REST request to get CrawlLog : {}", id);
        Optional<CrawlLogDTO> crawlLogDTO = crawlLogService.findOne(id);
        return ResponseUtil.wrapOrNotFound(crawlLogDTO);
    }

    /**
     * {@code DELETE  /crawl-logs/:id} : delete the "id" crawlLog.
     *
     * @param id the id of the crawlLogDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCrawlLog(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete CrawlLog : {}", id);
        crawlLogService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
