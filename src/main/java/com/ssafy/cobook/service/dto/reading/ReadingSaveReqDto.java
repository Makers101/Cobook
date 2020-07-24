package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.reading.Reading;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingSaveReqDto {

    private Long clubId;
    private Long bookId;
    private String title;
    private LocalDateTime datetime;
    private String place;
    private String description;
    private String oneline_description;

    public Reading toEntity() {
        return Reading.builder()
                .title(title)
                .dateTime(datetime)
                .place(place)
                .description(description)
                .oneLineDescription(oneline_description)
                .build();
    }
}
