package com.ssafy.cobook.service.dto.reading;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingSaveResDto {

    private Long id;

    public ReadingSaveResDto(Long readingId) {
        this.id = readingId;
    }
}
