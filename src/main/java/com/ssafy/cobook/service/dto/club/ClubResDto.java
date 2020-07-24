package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.genre.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubResDto {

    private Long clubId;
    private String clubImage;
    private String clubName;
    private String onelineDescription;
    private List<String> clubGenres;
    private Integer following;
    private Boolean recruit;

    public ClubResDto(Club club) {
        this.clubId = club.getId();
        this.clubImage = club.getClubImg();
        this.clubName = club.getName();
        this.onelineDescription = club.getOnelineDescription();
        this.clubGenres = club.getGenres().stream()
                .map(ClubGenre::getGenre)
                .map(Genre::getGenre)
                .collect(Collectors.toList());
        this.following = club.getFollowList().size();
        this.recruit = club.getRecruit();
    }
}
