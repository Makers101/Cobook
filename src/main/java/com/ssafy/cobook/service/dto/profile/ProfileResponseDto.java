package com.ssafy.cobook.service.dto.profile;

import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ProfileResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String description;
    private String profileImg;
    private List<GenreResponseDto> userGenres;

    public ProfileResponseDto(User user){
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
        this.description = user.getDescription();
        this.profileImg =user.getProfileImg();
        this.userGenres = user.getUserGenres().stream()
                .map(UserGenre::getGenre)
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
    }

}