package com.ssafy.cobook.service.dto.postcomment;

import com.ssafy.cobook.domain.postcomment.PostComment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsResDto {

    private Long postCommentId;
    private String content;
    private Long userId;
    private String username;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;

    public CommentsResDto(PostComment postComment) {
        this.postCommentId = postComment.getId();
        this.content = postComment.getContent();
        this.userId = postComment.getUser().getId();
        this.username = postComment.getUser().getUserName();
        this.createDateTime = postComment.getCreatDateTime();
        this.updateDateTime = postComment.getLastModifiedDate();
    }
}
