package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    private String nickName;
    private String description;
    private String profileImg;
    private List<GenreResponseDto> genreList;
}
