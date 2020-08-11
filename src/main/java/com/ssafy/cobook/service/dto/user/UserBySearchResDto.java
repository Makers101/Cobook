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

    public UserBySearchResDto(User user){
        this.id = user.getId();
        this.nickName = user.getNickName();
        this.profileImg = user.getProfileImg();
    }
}
