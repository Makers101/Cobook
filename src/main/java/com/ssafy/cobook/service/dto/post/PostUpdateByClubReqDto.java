package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostUpdateByClubReqDto {

    @ApiModelProperty(position = 1)
    private String onelineReview;
    @ApiModelProperty(position = 2)
    private Integer rank;
    @ApiModelProperty(position = 3)
    private Boolean open;
    @ApiModelProperty(position = 4)
    private String review;
    @ApiModelProperty(position = 5)
    private Boolean isClub;
    @ApiModelProperty(position = 6)
    private List<String> tags;

    public Post toEntity() {
        return Post.builder()
                .isClub(true)
                .review(review)
                .rank(rank)
                .open(open)
                .onelineReview(onelineReview)
                .build();
    }
}
