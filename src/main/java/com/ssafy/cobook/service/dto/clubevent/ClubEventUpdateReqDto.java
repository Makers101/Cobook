package com.ssafy.cobook.service.dto.clubevent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventUpdateReqDto {

    @ApiModelProperty(position = 1)
    private Long booKId;
    @ApiModelProperty(position = 2)
    private String title;
    @ApiModelProperty(position = 3)
    private LocalDateTime dateTime;
    @ApiModelProperty(position = 4)
    private String place;
    @ApiModelProperty(position = 5)
    private String description;
    @ApiModelProperty(position = 6)
    private Integer capacity;
    @ApiModelProperty(position = 7)
    private List<String> question;
}
