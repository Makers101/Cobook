package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.service.dto.clubmember.ClubMemberResponseDto;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
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
    private ClubMemberResponseDto leader;
    private List<ClubMemberResponseDto> members;
    private List<Long> candidates;
    private List<ReadingSimpleResDto> readings;

    public ClubDetailResDto(Club club) {
        this.id = club.getId();
        this.name = club.getName();
        this.clubImg = "http://i3a111.p.ssafy.io:8080/api/clubs/images/" + this.id;
        this.onelineDescription = club.getOnelineDescription();
        this.description = club.getDescription();
        this.residence = club.getResidence();
        this.recruit = club.getRecruit();
        this.createdAt = club.getCreatDateTime();
        this.updatedAt = club.getLastModifiedDate();
        this.followerCnt = club.getFollowList().size();
        this.genres = club.getGenres().stream()
                .map(ClubGenre::getGenre)
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
        this.leader = club.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.LEADER))
                .findFirst()
                .map(ClubMemberResponseDto::new)
                .get();
        this.members = club.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.MEMBER))
                .map(ClubMemberResponseDto::new)
                .collect(Collectors.toList());
        this.readings = club.getReadingList().stream()
                .map(ReadingSimpleResDto::new)
                .collect(Collectors.toList());
        this.memberCnt = members.size() + 1;
        this.candidates = club.getMembers().stream()
                .filter(m->m.getRole().equals(MemberRole.WAITING))
                .map(m->m.getUser().getId())
                .collect(Collectors.toList());
    }
}
