package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssafy.cobook.config.WebMvcConfig.SERVER_PORT;

@Getter
@NoArgsConstructor
public class UserBySearchResDto {
    private Long id;
    private String nickName;
    private String profileImg;

    public UserBySearchResDto(User user){
        this.id = user.getId();
        this.nickName = user.getNickName();
        if (user.getProfileImg() != null) {
            this.profileImg = "http://i3a111.p.ssafy.io:" + SERVER_PORT + "/api/profile/images/" + this.id;
        }
    }
}
