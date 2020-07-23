package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResDto {

    private Long postId;
    private Long userId;
    private Long clubId;
    private String onelineReview;
    private String review;
    private Integer rank;
    private Boolean open;
    private Boolean isClub;

    public PostDetailResDto(Post post) {
        this.postId = post.getId();
        if (post.ofClub()) {
            this.clubId = post.getClub().getId();
        } else {
            this.userId = post.getUser().getId();
        }
        this.onelineReview = post.getOnelineReview();
        this.review = post.getReview();
        this.rank = post.getRank();
        this.open = post.getOpen();
        this.isClub = post.getIsClub();
    }
}
