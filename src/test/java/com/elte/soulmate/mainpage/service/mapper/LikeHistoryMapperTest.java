package com.elte.soulmate.mainpage.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LikeHistoryMapperTest {

    private LikeHistoryMapper likeHistoryMapper;

    @BeforeEach
    public void setUp() {
        likeHistoryMapper = new LikeHistoryMapperImpl();
    }
}
