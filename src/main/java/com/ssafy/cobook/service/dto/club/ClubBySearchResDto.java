package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.service.dto.clubmember.ClubMemberResponseDto;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class ClubBySearchResDto {
    private Long id;
    private Integer memberCnt;
    private Integer followerCnt;
    private String clubImg;
    private String name;
    private String onelineDescription;
    private String description;
    private String residence;
    private Boolean recruit;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<GenreResponseDto> genres;

    public ClubBySearchResDto(Club club){
        this.id = club.getId();
        this.memberCnt = club.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.MEMBER))
                .map(ClubMemberResponseDto::new)
                .collect(Collectors.toList()).size()+1;
        this.followerCnt = club.getFollowList().size();
        this.clubImg = club.getClubImg();
        this.name = club.getName();
        this.onelineDescription = club.getOnelineDescription();
        this.description = club.getDescription();
        this.residence = club.getResidence();
        this.recruit = club.getRecruit();
        this.createdAt = club.getCreatDateTime();
        this.updatedAt = club.getLastModifiedDate();
        this.genres = club.getGenres().stream()
                .map(ClubGenre::getGenre)
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
    }
}
