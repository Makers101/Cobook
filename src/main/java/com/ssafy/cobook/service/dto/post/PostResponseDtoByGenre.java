package com.ssafy.cobook.service.dto.post;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDtoByGenre {

    private String genre;
    private List<PostResponseDto> posts;

    public PostResponseDtoByGenre(String genre) {
        this.genre = genre;
    }

    public void setPosts(List<PostResponseDto> dtos) {
        this.posts = dtos;
    }
}
