package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserBySearchResDto {
    private Long id;
    private String nickName;
    private String profileImg;
    private Boolean isFollow;

    public UserBySearchResDto(User user, Boolean isFollow) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        if (user.getProfileImg() != null) {
            this.profileImg = "https://i3a111.p.ssafy.io:8090/api/profile/images/" + this.id;
        }
        this.isFollow = isFollow;
    }
}
