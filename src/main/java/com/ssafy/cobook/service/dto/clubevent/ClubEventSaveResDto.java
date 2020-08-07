package com.ssafy.cobook.service.dto.clubevent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventSaveResDto {

    private Long id;

    public ClubEventSaveResDto(Long clubEventId) {
        this.id = clubEventId;
    }
}
