package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubUpdateRequestDto {

    @ApiModelProperty(position = 1)
    @Length(min = 1, max = 30)
    private String name;
    @ApiModelProperty(position = 2)
    @Length(min = 1, max = 30)
    private String onelineDescription;
    @ApiModelProperty(position = 3)
    @Length(min = 1, max = 100)
    private String description;
    @ApiModelProperty(position = 4)
    @Length(min = 1, max = 30)
    private String residence;
    @ApiModelProperty(position = 5)
    private List<Long> genres;

    public Club toEntity() {
        return Club.builder()
                .description(description)
                .onelineDescription(onelineDescription)
                .name(name)
                .residence(residence)
                .build();
    }
}
