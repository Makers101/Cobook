package com.ssafy.cobook.service.dto.clubmember;

import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ClubMemberResponseDto {

    private Long id;
    private String userName;
    private String profileImg;

    public ClubMemberResponseDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.profileImg = user.getProfileImg();
    }
}
