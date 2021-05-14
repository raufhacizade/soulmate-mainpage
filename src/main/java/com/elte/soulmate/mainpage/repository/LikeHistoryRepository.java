package com.elte.soulmate.mainpage.repository;

import com.elte.soulmate.mainpage.domain.LikeHistory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the LikeHistory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LikeHistoryRepository extends JpaRepository<LikeHistory, Long> {}
