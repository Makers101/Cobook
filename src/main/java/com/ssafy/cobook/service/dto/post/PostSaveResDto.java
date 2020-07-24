package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveResDto {

    private Long postId;

    public PostSaveResDto(Post post) {
        this.postId = post.getId();
    }
}
