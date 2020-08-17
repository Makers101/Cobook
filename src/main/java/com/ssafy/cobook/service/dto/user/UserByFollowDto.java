package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserByFollowDto {
    Long id;
    String nickname;
    String profileImg;
    Boolean isFollow; // 내가 toUser를 팔로우 했는지 안했는지 여부라고 보면됨!

    public UserByFollowDto(User user) {
        this.id = user.getId();
        this.nickname = user.getNickName();
        if (user.getProfileImg() != null) {
            this.profileImg = "https://i3a111.p.ssafy.io:8090/api/profile/images/" + this.id;
        }
        this.isFollow = false;
    }

    public void setIsFollow(Boolean isFollow) {
        this.isFollow = isFollow;
    }

}
