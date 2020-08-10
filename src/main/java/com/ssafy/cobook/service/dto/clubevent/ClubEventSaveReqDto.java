package com.ssafy.cobook.service.dto.clubevent;

import com.ssafy.cobook.domain.clubevent.ClubEvent;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventSaveReqDto {

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

    public ClubEvent toEntity() {
        return ClubEvent.builder()
                .title(name)
                .dateTime(datetime)
                .place(place)
                .description(description)
                .closed(false)
                .build();
    }
}
