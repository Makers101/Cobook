package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubByPostDto {

    private Long id;
    private String name;
    private String clubImg;

    public ClubByPostDto(Club club) {
        this.id = club.getId();
        this.name = club.getName();
        if (club.getClubImg() != null) {
            this.clubImg = "https://i3a111.p.ssafy.io:8090/api/clubs/images/" + this.id;
        }
    }
}
