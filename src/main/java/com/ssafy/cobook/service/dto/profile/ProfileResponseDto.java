package com.ssafy.cobook.service.dto.profile;

import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.service.dto.club.ClubResDto;
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
    private String nickName;
    private String description;
    private String profileImg;
    private List<GenreResponseDto> likeGenres;
    private List<ClubResDto> myClubs;

    public ProfileResponseDto(User user, List<ClubResDto> clubResDto) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.description = user.getDescription();
        if (user.getProfileImg() != null) {
            this.profileImg = "http://i3a111.p.ssafy.io:8080/api/profile/images/" + this.id;
        }
        this.likeGenres = user.getUserGenres().stream()
                .map(UserGenre::getGenre)
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
        this.myClubs = clubResDto;
    }

}
