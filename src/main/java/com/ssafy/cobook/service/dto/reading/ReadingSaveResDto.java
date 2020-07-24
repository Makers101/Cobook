package com.ssafy.cobook.service.dto.reading;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingSaveResDto {

    private Long readingId;

    public ReadingSaveResDto(Long readingId) {
        this.readingId = readingId;
    }
}
