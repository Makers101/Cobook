package com.ssafy.cobook.service.dto.clubevent;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventUpdateReqDto {

    @ApiModelProperty(position = 1)
    private Long bookId;
    @ApiModelProperty(position = 2)
    @Length(min = 1, max = 30)
    private String name;
    @ApiModelProperty(position = 3)
    private LocalDateTime datetime;
    @ApiModelProperty(position = 4)
    @Length(min = 1, max = 30)
    private String place;
    @ApiModelProperty(position = 5)
    @Length(min = 1, max = 100)
    private String description;
    @ApiModelProperty(position = 6)
    private List<String> questions;
}
