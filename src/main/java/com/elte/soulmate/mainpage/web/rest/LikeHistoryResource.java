package com.elte.soulmate.mainpage.web.rest;

import com.elte.soulmate.mainpage.repository.LikeHistoryRepository;
import com.elte.soulmate.mainpage.service.LikeHistoryService;
import com.elte.soulmate.mainpage.service.dto.LikeHistoryDTO;
import com.elte.soulmate.mainpage.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.elte.soulmate.mainpage.domain.LikeHistory}.
 */
@RestController
@RequestMapping("/api")
public class LikeHistoryResource {

    private final Logger log = LoggerFactory.getLogger(LikeHistoryResource.class);

    private static final String ENTITY_NAME = "mainPageLikeHistory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LikeHistoryService likeHistoryService;

    private final LikeHistoryRepository likeHistoryRepository;

    public LikeHistoryResource(LikeHistoryService likeHistoryService, LikeHistoryRepository likeHistoryRepository) {
        this.likeHistoryService = likeHistoryService;
        this.likeHistoryRepository = likeHistoryRepository;
    }

    /**
     * {@code POST  /like-histories} : Create a new likeHistory.
     *
     * @param likeHistoryDTO the likeHistoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new likeHistoryDTO, or with status {@code 400 (Bad Request)} if the likeHistory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/like-histories")
    public ResponseEntity<LikeHistoryDTO> createLikeHistory(@Valid @RequestBody LikeHistoryDTO likeHistoryDTO) throws URISyntaxException {
        log.debug("REST request to save LikeHistory : {}", likeHistoryDTO);
        if (likeHistoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new likeHistory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LikeHistoryDTO result = likeHistoryService.save(likeHistoryDTO);
        return ResponseEntity
            .created(new URI("/api/like-histories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /like-histories/:id} : Updates an existing likeHistory.
     *
     * @param id the id of the likeHistoryDTO to save.
     * @param likeHistoryDTO the likeHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the likeHistoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the likeHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/like-histories/{id}")
    public ResponseEntity<LikeHistoryDTO> updateLikeHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody LikeHistoryDTO likeHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to update LikeHistory : {}, {}", id, likeHistoryDTO);
        if (likeHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likeHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        LikeHistoryDTO result = likeHistoryService.save(likeHistoryDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, likeHistoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /like-histories/:id} : Partial updates given fields of an existing likeHistory, field will ignore if it is null
     *
     * @param id the id of the likeHistoryDTO to save.
     * @param likeHistoryDTO the likeHistoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated likeHistoryDTO,
     * or with status {@code 400 (Bad Request)} if the likeHistoryDTO is not valid,
     * or with status {@code 404 (Not Found)} if the likeHistoryDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the likeHistoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/like-histories/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<LikeHistoryDTO> partialUpdateLikeHistory(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody LikeHistoryDTO likeHistoryDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update LikeHistory partially : {}, {}", id, likeHistoryDTO);
        if (likeHistoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, likeHistoryDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!likeHistoryRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<LikeHistoryDTO> result = likeHistoryService.partialUpdate(likeHistoryDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, likeHistoryDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /like-histories} : get all the likeHistories.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of likeHistories in body.
     */
    @GetMapping("/like-histories")
    public List<LikeHistoryDTO> getAllLikeHistories() {
        log.debug("REST request to get all LikeHistories");
        return likeHistoryService.findAll();
    }

    /**
     * {@code GET  /like-histories/:id} : get the "id" likeHistory.
     *
     * @param id the id of the likeHistoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the likeHistoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/like-histories/{id}")
    public ResponseEntity<LikeHistoryDTO> getLikeHistory(@PathVariable Long id) {
        log.debug("REST request to get LikeHistory : {}", id);
        Optional<LikeHistoryDTO> likeHistoryDTO = likeHistoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(likeHistoryDTO);
    }

    /**
     * {@code DELETE  /like-histories/:id} : delete the "id" likeHistory.
     *
     * @param id the id of the likeHistoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/like-histories/{id}")
    public ResponseEntity<Void> deleteLikeHistory(@PathVariable Long id) {
        log.debug("REST request to delete LikeHistory : {}", id);
        likeHistoryService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }
}
