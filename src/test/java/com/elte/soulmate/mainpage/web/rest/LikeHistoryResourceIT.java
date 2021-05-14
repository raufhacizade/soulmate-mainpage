package com.elte.soulmate.mainpage.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.elte.soulmate.mainpage.IntegrationTest;
import com.elte.soulmate.mainpage.domain.LikeHistory;
import com.elte.soulmate.mainpage.repository.LikeHistoryRepository;
import com.elte.soulmate.mainpage.service.dto.LikeHistoryDTO;
import com.elte.soulmate.mainpage.service.mapper.LikeHistoryMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link LikeHistoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class LikeHistoryResourceIT {

    private static final Long DEFAULT_WHO_LIKE_ID = 1L;
    private static final Long UPDATED_WHO_LIKE_ID = 2L;

    private static final Long DEFAULT_LIKED_PERSON_ID = 1L;
    private static final Long UPDATED_LIKED_PERSON_ID = 2L;

    private static final LocalDate DEFAULT_SEND_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_SEND_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String ENTITY_API_URL = "/api/like-histories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private LikeHistoryRepository likeHistoryRepository;

    @Autowired
    private LikeHistoryMapper likeHistoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLikeHistoryMockMvc;

    private LikeHistory likeHistory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeHistory createEntity(EntityManager em) {
        LikeHistory likeHistory = new LikeHistory()
            .whoLikeId(DEFAULT_WHO_LIKE_ID)
            .likedPersonId(DEFAULT_LIKED_PERSON_ID)
            .sendDate(DEFAULT_SEND_DATE);
        return likeHistory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static LikeHistory createUpdatedEntity(EntityManager em) {
        LikeHistory likeHistory = new LikeHistory()
            .whoLikeId(UPDATED_WHO_LIKE_ID)
            .likedPersonId(UPDATED_LIKED_PERSON_ID)
            .sendDate(UPDATED_SEND_DATE);
        return likeHistory;
    }

    @BeforeEach
    public void initTest() {
        likeHistory = createEntity(em);
    }

    @Test
    @Transactional
    void createLikeHistory() throws Exception {
        int databaseSizeBeforeCreate = likeHistoryRepository.findAll().size();
        // Create the LikeHistory
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);
        restLikeHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeCreate + 1);
        LikeHistory testLikeHistory = likeHistoryList.get(likeHistoryList.size() - 1);
        assertThat(testLikeHistory.getWhoLikeId()).isEqualTo(DEFAULT_WHO_LIKE_ID);
        assertThat(testLikeHistory.getLikedPersonId()).isEqualTo(DEFAULT_LIKED_PERSON_ID);
        assertThat(testLikeHistory.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
    }

    @Test
    @Transactional
    void createLikeHistoryWithExistingId() throws Exception {
        // Create the LikeHistory with an existing ID
        likeHistory.setId(1L);
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        int databaseSizeBeforeCreate = likeHistoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restLikeHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkWhoLikeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeHistoryRepository.findAll().size();
        // set the field null
        likeHistory.setWhoLikeId(null);

        // Create the LikeHistory, which fails.
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        restLikeHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkLikedPersonIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = likeHistoryRepository.findAll().size();
        // set the field null
        likeHistory.setLikedPersonId(null);

        // Create the LikeHistory, which fails.
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        restLikeHistoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllLikeHistories() throws Exception {
        // Initialize the database
        likeHistoryRepository.saveAndFlush(likeHistory);

        // Get all the likeHistoryList
        restLikeHistoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(likeHistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].whoLikeId").value(hasItem(DEFAULT_WHO_LIKE_ID.intValue())))
            .andExpect(jsonPath("$.[*].likedPersonId").value(hasItem(DEFAULT_LIKED_PERSON_ID.intValue())))
            .andExpect(jsonPath("$.[*].sendDate").value(hasItem(DEFAULT_SEND_DATE.toString())));
    }

    @Test
    @Transactional
    void getLikeHistory() throws Exception {
        // Initialize the database
        likeHistoryRepository.saveAndFlush(likeHistory);

        // Get the likeHistory
        restLikeHistoryMockMvc
            .perform(get(ENTITY_API_URL_ID, likeHistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(likeHistory.getId().intValue()))
            .andExpect(jsonPath("$.whoLikeId").value(DEFAULT_WHO_LIKE_ID.intValue()))
            .andExpect(jsonPath("$.likedPersonId").value(DEFAULT_LIKED_PERSON_ID.intValue()))
            .andExpect(jsonPath("$.sendDate").value(DEFAULT_SEND_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingLikeHistory() throws Exception {
        // Get the likeHistory
        restLikeHistoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewLikeHistory() throws Exception {
        // Initialize the database
        likeHistoryRepository.saveAndFlush(likeHistory);

        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();

        // Update the likeHistory
        LikeHistory updatedLikeHistory = likeHistoryRepository.findById(likeHistory.getId()).get();
        // Disconnect from session so that the updates on updatedLikeHistory are not directly saved in db
        em.detach(updatedLikeHistory);
        updatedLikeHistory.whoLikeId(UPDATED_WHO_LIKE_ID).likedPersonId(UPDATED_LIKED_PERSON_ID).sendDate(UPDATED_SEND_DATE);
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(updatedLikeHistory);

        restLikeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, likeHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
        LikeHistory testLikeHistory = likeHistoryList.get(likeHistoryList.size() - 1);
        assertThat(testLikeHistory.getWhoLikeId()).isEqualTo(UPDATED_WHO_LIKE_ID);
        assertThat(testLikeHistory.getLikedPersonId()).isEqualTo(UPDATED_LIKED_PERSON_ID);
        assertThat(testLikeHistory.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    void putNonExistingLikeHistory() throws Exception {
        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();
        likeHistory.setId(count.incrementAndGet());

        // Create the LikeHistory
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, likeHistoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchLikeHistory() throws Exception {
        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();
        likeHistory.setId(count.incrementAndGet());

        // Create the LikeHistory
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeHistoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamLikeHistory() throws Exception {
        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();
        likeHistory.setId(count.incrementAndGet());

        // Create the LikeHistory
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeHistoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateLikeHistoryWithPatch() throws Exception {
        // Initialize the database
        likeHistoryRepository.saveAndFlush(likeHistory);

        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();

        // Update the likeHistory using partial update
        LikeHistory partialUpdatedLikeHistory = new LikeHistory();
        partialUpdatedLikeHistory.setId(likeHistory.getId());

        restLikeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLikeHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLikeHistory))
            )
            .andExpect(status().isOk());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
        LikeHistory testLikeHistory = likeHistoryList.get(likeHistoryList.size() - 1);
        assertThat(testLikeHistory.getWhoLikeId()).isEqualTo(DEFAULT_WHO_LIKE_ID);
        assertThat(testLikeHistory.getLikedPersonId()).isEqualTo(DEFAULT_LIKED_PERSON_ID);
        assertThat(testLikeHistory.getSendDate()).isEqualTo(DEFAULT_SEND_DATE);
    }

    @Test
    @Transactional
    void fullUpdateLikeHistoryWithPatch() throws Exception {
        // Initialize the database
        likeHistoryRepository.saveAndFlush(likeHistory);

        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();

        // Update the likeHistory using partial update
        LikeHistory partialUpdatedLikeHistory = new LikeHistory();
        partialUpdatedLikeHistory.setId(likeHistory.getId());

        partialUpdatedLikeHistory.whoLikeId(UPDATED_WHO_LIKE_ID).likedPersonId(UPDATED_LIKED_PERSON_ID).sendDate(UPDATED_SEND_DATE);

        restLikeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedLikeHistory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedLikeHistory))
            )
            .andExpect(status().isOk());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
        LikeHistory testLikeHistory = likeHistoryList.get(likeHistoryList.size() - 1);
        assertThat(testLikeHistory.getWhoLikeId()).isEqualTo(UPDATED_WHO_LIKE_ID);
        assertThat(testLikeHistory.getLikedPersonId()).isEqualTo(UPDATED_LIKED_PERSON_ID);
        assertThat(testLikeHistory.getSendDate()).isEqualTo(UPDATED_SEND_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingLikeHistory() throws Exception {
        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();
        likeHistory.setId(count.incrementAndGet());

        // Create the LikeHistory
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLikeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, likeHistoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchLikeHistory() throws Exception {
        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();
        likeHistory.setId(count.incrementAndGet());

        // Create the LikeHistory
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamLikeHistory() throws Exception {
        int databaseSizeBeforeUpdate = likeHistoryRepository.findAll().size();
        likeHistory.setId(count.incrementAndGet());

        // Create the LikeHistory
        LikeHistoryDTO likeHistoryDTO = likeHistoryMapper.toDto(likeHistory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restLikeHistoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(likeHistoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the LikeHistory in the database
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteLikeHistory() throws Exception {
        // Initialize the database
        likeHistoryRepository.saveAndFlush(likeHistory);

        int databaseSizeBeforeDelete = likeHistoryRepository.findAll().size();

        // Delete the likeHistory
        restLikeHistoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, likeHistory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<LikeHistory> likeHistoryList = likeHistoryRepository.findAll();
        assertThat(likeHistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
