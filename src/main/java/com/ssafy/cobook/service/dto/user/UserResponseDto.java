package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
    private String nickName;
    private String description;
    private String profileImg;
    private PlatformType platformType;
    private String platformId;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.nickName = user.getNickName();
        this.description = user.getDescription();
        if (user.getProfileImg() != null) {
            this.profileImg = "https://i3a111.p.ssafy.io:8090/api/profile/images/" + this.id;
        }
        this.platformType = user.getPlatformType();
        this.platformId = user.getPlatformId();
    }
}
