package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class ClubCreateReqDto {

    @ApiModelProperty(position = 1)
    private String name;
    @ApiModelProperty(position = 2)
    private String onelineDescription;
    @ApiModelProperty(position = 3)
    private String description;
    @ApiModelProperty(position = 4)
    private String residence;
    @ApiModelProperty(position = 5)
    private List<Long> genres;
    @ApiModelProperty(position = 6)
    private List<Long> members;

    public Club toEntity() {
        return Club.builder()
                .description(description)
                .onelineDescription(onelineDescription)
                .name(name)
                .residence(residence)
                .build();
    }
}
