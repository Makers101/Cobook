package com.ssafy.cobook.service.dto.onedayevent;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventSaveResDto {

    private Long oneDayEventId;

    public OneDayEventSaveResDto(Long oneDayEventId) {
        this.oneDayEventId = oneDayEventId;
    }
}
