package com.ssafy.cobook.service.dto.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateReqDto {

    @ApiModelProperty(position = 1)
    @Length(min =1, max = 30)
    private String onelineReview;
    @ApiModelProperty(position = 2)
    private Integer rank;
    @ApiModelProperty(position = 3)
    private Boolean open;
    @ApiModelProperty(position = 4)
    @Length(min =1, max = 32000)
    private String review;
    @ApiModelProperty(position = 5)
    private List<String> tags;
}
