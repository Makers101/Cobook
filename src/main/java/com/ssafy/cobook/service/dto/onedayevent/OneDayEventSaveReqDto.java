package com.ssafy.cobook.service.dto.onedayevent;

import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventSaveReqDto {

    @ApiModelProperty(position = 1)
    @Length(min = 1, max = 30)
    private String name;
    @ApiModelProperty(position = 2)
    @Length(min = 1, max = 30)
    private String description;
    @ApiModelProperty(position = 3)
    private LocalDateTime datetime;
    @ApiModelProperty(position = 4)
    @Length(min = 1, max = 30)
    private String place;
    @ApiModelProperty(position = 5)
    private Long bookId;
    @ApiModelProperty(position = 6)
    private Integer capacity;
    @ApiModelProperty(position = 7)
    private List<String> questions;

    public OneDayEvent toEntity() {
        return OneDayEvent.builder()
                .title(name)
                .description(description)
                .dateTime(datetime)
                .place(place)
                .closed(false)
                .capacity(capacity)
                .build();
    }
}
