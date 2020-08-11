package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.clubmember.ClubMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCandidatesResponseDto {

    private Long clubMemberId;
    private Long userId;
    private String nickName;
    private String profileImg;

    public ClubCandidatesResponseDto(ClubMember clubMember) {
        this.clubMemberId = clubMember.getId();
        this.userId = clubMember.getUser().getId();
        this.nickName = clubMember.getUser().getNickName();
        if (clubMember.getUser().getProfileImg() != null) {
            this.profileImg = "https://i3a111.p.ssafy.io/api/profile/images/" + this.userId;
        }
    }
}
