package com.ssafy.cobook.service.dto.profile;

import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.service.dto.club.ClubResDto;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import com.ssafy.cobook.service.dto.user.UserByFollowDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.cobook.config.WebMvcConfig.SERVER_PORT;

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
    private List<UserByFollowDto> followerList;
    private List<UserByFollowDto> followingList;

    public ProfileResponseDto(User user, List<ClubResDto> clubResDto, List<UserByFollowDto> followerList, List<UserByFollowDto> followingList) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.nickName = user.getNickName();
        this.description = user.getDescription();
        if (user.getProfileImg() != null) {
            this.profileImg = "http://i3a111.p.ssafy.io:" + SERVER_PORT + "/api/profile/images/" + this.id;
        }

        this.likeGenres = user.getUserGenres().stream()
                .map(UserGenre::getGenre)
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
        this.myClubs = clubResDto;
        this.followerList = followerList;
        this.followingList = followingList;
    }
}
