package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSaveResDto {

    private Long id;

    public PostSaveResDto(Post post) {
        this.id = post.getId();
    }
}
