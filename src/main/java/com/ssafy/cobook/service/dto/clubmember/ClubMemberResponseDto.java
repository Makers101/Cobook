package com.ssafy.cobook.service.dto.clubmember;

import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.cobook.config.WebMvcConfig.SERVER_PORT;

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
            this.profileImg = "http://i3a111.p.ssafy.io:" + SERVER_PORT + "/api/profile/images/" + this.id;
        }
    }
}
