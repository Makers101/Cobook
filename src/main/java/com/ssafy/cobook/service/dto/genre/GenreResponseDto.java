package com.ssafy.cobook.service.dto.genre;

import com.ssafy.cobook.domain.genre.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GenreResponseDto {

    private Long id;
    private String name;

    public GenreResponseDto(Genre genre) {
        this.id = genre.getId();
        this.name = genre.getGenreName();
    }
}
