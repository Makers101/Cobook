package com.ssafy.cobook.service.dto.onedayevent;

import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMember;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.cobook.config.WebMvcConfig.SERVER_PORT;

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
            this.profileImg = "http://i3a111.p.ssafy.io:" + SERVER_PORT + " /api/profile/images/" + this.id;
        }
    }
}
