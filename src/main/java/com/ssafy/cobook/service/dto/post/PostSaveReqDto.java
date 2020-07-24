package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveReqDto {

    private Long bookId;
    private Long clubId;
    private Long userId;
    private String onelineReview;
    private String review;
    private Integer rank;
    private Boolean open;
    private Boolean isClub;
    private List<String> tags;

    public Post toEntity() {
        return Post.builder()
                .onelineReview(onelineReview)
                .review(review)
                .rank(rank)
                .open(open)
                .build();
    }

    public boolean isClub() {
        return isClub;
    }
}
