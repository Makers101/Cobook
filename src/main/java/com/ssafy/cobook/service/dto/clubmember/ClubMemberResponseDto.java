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
        this.profileImg = user.getProfileImg();
    }
}
