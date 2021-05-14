package com.elte.soulmate.mainpage.service.mapper;

import com.elte.soulmate.mainpage.domain.*;
import com.elte.soulmate.mainpage.service.dto.LikeHistoryDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LikeHistory} and its DTO {@link LikeHistoryDTO}.
 */
@Mapper(componentModel = "spring", uses = { PersonMapper.class })
public interface LikeHistoryMapper extends EntityMapper<LikeHistoryDTO, LikeHistory> {
    @Mapping(target = "whoLiked", source = "whoLiked", qualifiedByName = "id")
    @Mapping(target = "likedPerson", source = "likedPerson", qualifiedByName = "id")
    LikeHistoryDTO toDto(LikeHistory s);
}
