package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserByPostDto {

    private Long id;
    private String nickName;
    private String profileImg;

    public UserByPostDto(User user) {
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.profileImg = "http://i3a111.p.ssafy.io:8080/api/profile/images/" + this.id;
    }
}
