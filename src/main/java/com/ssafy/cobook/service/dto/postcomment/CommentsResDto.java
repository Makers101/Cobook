package com.ssafy.cobook.service.dto.postcomment;

import com.ssafy.cobook.domain.postcomment.PostComment;
import com.ssafy.cobook.service.dto.user.UserByPostDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsResDto {

    private Long id;
    private UserByPostDto user;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentsResDto(PostComment postComment) {
        this.id = postComment.getId();
        this.content = postComment.getContent();
        this.user = new UserByPostDto(postComment.getUser());
        this.createdAt = postComment.getCreatDateTime();
        this.updatedAt = postComment.getLastModifiedDate();
    }
}
