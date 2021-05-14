package com.elte.soulmate.mainpage.service.impl;

import com.elte.soulmate.mainpage.domain.LikeHistory;
import com.elte.soulmate.mainpage.repository.LikeHistoryRepository;
import com.elte.soulmate.mainpage.service.LikeHistoryService;
import com.elte.soulmate.mainpage.service.dto.LikeHistoryDTO;
import com.elte.soulmate.mainpage.service.mapper.LikeHistoryMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link LikeHistory}.
 */
@Service
@Transactional
public class LikeHistoryServiceImpl implements LikeHistoryService {

    private final Logger log = LoggerFactory.getLogger(LikeHistoryServiceImpl.class);

    private final LikeHistoryRepository likeHistoryRepository;

    private final LikeHistoryMapper likeHistoryMapper;

    public LikeHistoryServiceImpl(LikeHistoryRepository likeHistoryRepository, LikeHistoryMapper likeHistoryMapper) {
        this.likeHistoryRepository = likeHistoryRepository;
        this.likeHistoryMapper = likeHistoryMapper;
    }

    @Override
    public LikeHistoryDTO save(LikeHistoryDTO likeHistoryDTO) {
        log.debug("Request to save LikeHistory : {}", likeHistoryDTO);
        LikeHistory likeHistory = likeHistoryMapper.toEntity(likeHistoryDTO);
        likeHistory = likeHistoryRepository.save(likeHistory);
        return likeHistoryMapper.toDto(likeHistory);
    }

    @Override
    public Optional<LikeHistoryDTO> partialUpdate(LikeHistoryDTO likeHistoryDTO) {
        log.debug("Request to partially update LikeHistory : {}", likeHistoryDTO);

        return likeHistoryRepository
            .findById(likeHistoryDTO.getId())
            .map(
                existingLikeHistory -> {
                    likeHistoryMapper.partialUpdate(existingLikeHistory, likeHistoryDTO);
                    return existingLikeHistory;
                }
            )
            .map(likeHistoryRepository::save)
            .map(likeHistoryMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LikeHistoryDTO> findAll() {
        log.debug("Request to get all LikeHistories");
        return likeHistoryRepository.findAll().stream().map(likeHistoryMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<LikeHistoryDTO> findOne(Long id) {
        log.debug("Request to get LikeHistory : {}", id);
        return likeHistoryRepository.findById(id).map(likeHistoryMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete LikeHistory : {}", id);
        likeHistoryRepository.deleteById(id);
    }
}
