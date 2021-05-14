package com.elte.soulmate.mainpage.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.elte.soulmate.mainpage.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LikeHistoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LikeHistory.class);
        LikeHistory likeHistory1 = new LikeHistory();
        likeHistory1.setId(1L);
        LikeHistory likeHistory2 = new LikeHistory();
        likeHistory2.setId(likeHistory1.getId());
        assertThat(likeHistory1).isEqualTo(likeHistory2);
        likeHistory2.setId(2L);
        assertThat(likeHistory1).isNotEqualTo(likeHistory2);
        likeHistory1.setId(null);
        assertThat(likeHistory1).isNotEqualTo(likeHistory2);
    }
}
