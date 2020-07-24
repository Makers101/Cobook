package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.service.dto.reading.ReadingSimpleResDto;
import com.ssafy.cobook.service.dto.user.UserSimpleResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubDetailResDto {

    private Long clubId;
    private String clubName;
    private String onelineDescription;
    private String description;
    private String clubImage;
    private String residence;
    private LocalDateTime createDateTime;
    private LocalDateTime lastModifiedTime;
    private Integer parcipantCount;
    private Boolean recruit;
    private List<UserSimpleResDto> users;
    private List<String> genres;
    private List<ReadingSimpleResDto> readings;

    public ClubDetailResDto(Club club, List<UserSimpleResDto> users) {
        this.clubId = club.getId();
        this.clubName = club.getName();
        this.onelineDescription = club.getOnelineDescription();
        this.description = club.getDescription();
        this.clubImage = club.getClubImg();
        this.residence = club.getResidence();
        this.createDateTime = club.getCreatDateTime();
        this.lastModifiedTime = club.getLastModifiedDate();
        this.parcipantCount = club.getParticipantCount();
        this.recruit = club.getRecruit();
        this.users = users;
        this.genres = club.getGenres().stream()
                .map(ClubGenre::getGenre)
                .map(Genre::getGenre)
                .collect(Collectors.toList());
        this.readings = club.getReadingList().stream()
                .map(ReadingSimpleResDto::new)
                .collect(Collectors.toList());
    }
}
