package com.ssafy.cobook.service.dto.onedayevent;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventSaveResDto {

    private Long oneDayEventId;

    public OneDayEventSaveResDto(Long oneDayEventId) {
        this.oneDayEventId = oneDayEventId;
    }
}
