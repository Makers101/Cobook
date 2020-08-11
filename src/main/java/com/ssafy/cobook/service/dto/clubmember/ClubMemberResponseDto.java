package com.ssafy.cobook.service.dto.clubmember;

import com.ssafy.cobook.domain.clubmember.ClubMember;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubMemberResponseDto {

    private Long id;
    private String nickName;
    private String profileImg;

    public ClubMemberResponseDto(ClubMember clubMember) {
        this.id = clubMember.getUser().getId();
        this.nickName = clubMember.getUser().getNickName();
        if (clubMember.getUser().getProfileImg() != null) {
            this.profileImg = "https://i3a111.p.ssafy.io:8090/api/profile/images/" + this.id;
        }
    }
}
