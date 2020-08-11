package com.ssafy.cobook.service.dto.onedayevent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventUpdateReqDto {

    @ApiModelProperty(position = 1)
    private Long bookId;
    @ApiModelProperty(position = 2)
    private String name;
    @ApiModelProperty(position = 3)
    private LocalDateTime datetime;
    @ApiModelProperty(position = 4)
    private String place;
    @ApiModelProperty(position = 5)
    private String description;
    @ApiModelProperty(position = 6)
    private Integer capacity;
    @ApiModelProperty(position = 7)
    private List<String> questions;

}