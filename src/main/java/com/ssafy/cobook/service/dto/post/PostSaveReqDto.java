package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveReqDto {

    @ApiModelProperty(position = 1)
    private Long bookId;
    @ApiModelProperty(position = 2)
    private String onelineReview;
    @ApiModelProperty(position = 3)
    private Integer rank;
    @ApiModelProperty(position = 4)
    private Boolean open;
    @ApiModelProperty(position = 5)
    private String review;
    @ApiModelProperty(position = 6)
    private List<String> tags;

    public Post toEntity() {
        return Post.builder()
                .onelineReview(onelineReview)
                .review(review)
                .rank(rank)
                .open(open)
                .build();
    }
}
