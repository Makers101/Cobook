package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubResDto {

    private Long id;
    private String name;
    private String clubImg;
    private String onelineDescription;
    private String description;
    private String residence;
    private Boolean recruit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Integer memberCnt;
    private Integer followerCnt;
    private List<GenreResponseDto> genres;

    public ClubResDto(Club club) {
        this.id = club.getId();
        this.name = club.getName();
        this.clubImg = "http://i3a111.p.ssafy.io:8080/api/clubs/images/" + this.id;
        this.onelineDescription = club.getOnelineDescription();
        this.description = club.getDescription();
        this.residence = club.getResidence();
        this.recruit = club.getRecruit();
        this.createdAt = club.getCreatDateTime();
        this.updatedAt = club.getLastModifiedDate();
        this.memberCnt = club.getMembers().size();
        this.followerCnt = club.getFollowList().size();
        this.genres = club.getGenres().stream()
                .map(ClubGenre::getGenre)
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
    }
}
