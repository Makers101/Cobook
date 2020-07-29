package com.ssafy.cobook.service.dto.clubmember;

import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubMemberResponseDto {

    private Long id;
    private String nickName;
    private String profileImg;

    public ClubMemberResponseDto(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        if (user.getProfileImg() != null) {
            this.profileImg = "http://i3a111.p.ssafy.io:8080/api/profile/images/" + this.id;
        }
    }
}
