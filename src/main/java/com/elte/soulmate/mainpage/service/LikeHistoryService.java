package com.elte.soulmate.mainpage.service;

import com.elte.soulmate.mainpage.service.dto.LikeHistoryDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.elte.soulmate.mainpage.domain.LikeHistory}.
 */
public interface LikeHistoryService {
    /**
     * Save a likeHistory.
     *
     * @param likeHistoryDTO the entity to save.
     * @return the persisted entity.
     */
    LikeHistoryDTO save(LikeHistoryDTO likeHistoryDTO);

    /**
     * Partially updates a likeHistory.
     *
     * @param likeHistoryDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LikeHistoryDTO> partialUpdate(LikeHistoryDTO likeHistoryDTO);

    /**
     * Get all the likeHistories.
     *
     * @return the list of entities.
     */
    List<LikeHistoryDTO> findAll();

    /**
     * Get the "id" likeHistory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LikeHistoryDTO> findOne(Long id);

    /**
     * Delete the "id" likeHistory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
