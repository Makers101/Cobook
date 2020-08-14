package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.genre.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDtoByGenre {

    private Long genreId;
    private String genre;
    private List<PostResponseDto> posts;

    public PostResponseDtoByGenre(Genre genre) {
        this.genreId = genre.getId();
        this.genre = genre.getGenreName();
    }

    public void setPosts(List<PostResponseDto> dtos) {
        this.posts = dtos;
    }
}
