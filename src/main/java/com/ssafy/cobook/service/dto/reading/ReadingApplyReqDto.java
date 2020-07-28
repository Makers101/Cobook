package com.ssafy.cobook.service.dto.reading;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingApplyReqDto {

    private Long userId;
    private Long clubId;
    private Long readingId;
}
