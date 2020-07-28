package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.reading.Reading;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingSaveReqDto {

    @ApiModelProperty(position = 1)
    private String name;
    @ApiModelProperty(position = 2)
    private String description;
    @ApiModelProperty(position = 3)
    private LocalDateTime datetime;
    @ApiModelProperty(position = 4)
    private String place;
    @ApiModelProperty(position = 5)
    private Long bookId;
    @ApiModelProperty(position = 6)
    private List<String> questions;

    public Reading toEntity() {
        return Reading.builder()
                .title(name)
                .dateTime(datetime)
                .place(place)
                .description(description)
                .build();
    }
}
