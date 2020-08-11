package com.ssafy.cobook.service.dto.onedayevent;

import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMember;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventMemberResponseDto {

    private Long id;
    private String nickName;
    private String profileImg;

    public OneDayEventMemberResponseDto(OneDayEventMember member) {
        User user = member.getUser();
        this.id = user.getId();
        this.nickName = user.getNickName();
        if (user.getProfileImg() != null) {
            this.profileImg = "https://i3a111.p.ssafy.io:8090/api/profile/images/" + this.id;
        }
    }
}
