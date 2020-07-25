package com.ssafy.cobook.service.dto.postcomment;

import com.ssafy.cobook.domain.postcomment.PostComment;
import com.ssafy.cobook.service.dto.user.UserByPostDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsResDto {

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private UserByPostDto user;
    @ApiModelProperty(position = 3)
    private String content;
    @ApiModelProperty(position = 4)
    private String createAt;
    @ApiModelProperty(position = 5)
    private String updateAt;

    public CommentsResDto(PostComment postComment) {
        this.id = postComment.getId();
        this.content = postComment.getContent();
        this.user = new UserByPostDto(postComment.getUser());
        this.createAt = postComment.getCreatDateTime().toString();
        this.updateAt = postComment.getLastModifiedDate().toString();
    }
}
