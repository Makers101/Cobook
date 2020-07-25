package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserByPostDto {

    private Long id;
    private String userName;
    private String profileImg;

    public UserByPostDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.profileImg = user.getProfileImg();
    }
}
