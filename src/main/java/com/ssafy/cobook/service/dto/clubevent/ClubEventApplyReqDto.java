package com.ssafy.cobook.service.dto.clubevent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventApplyReqDto {

    private Long userId;
    private Long clubId;
    private Long clubEventId;
}
