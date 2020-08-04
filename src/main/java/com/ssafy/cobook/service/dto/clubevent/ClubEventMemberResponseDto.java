package com.ssafy.cobook.service.dto.clubevent;

import com.ssafy.cobook.domain.clubeventmember.ClubEventMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventMemberResponseDto {

    private Long id;
    private String nickName;
    private String profileImg;

    public ClubEventMemberResponseDto(ClubEventMember clubEventMember) {
        this.id = clubEventMember.getUser().getId();
        this.nickName = clubEventMember.getUser().getNickName();
        if (clubEventMember.getUser().getProfileImg() != null) {
            this.profileImg = "http://i3a111.p.ssafy.io:8080/api/profile/images/" + this.id;
        }
    }
}
