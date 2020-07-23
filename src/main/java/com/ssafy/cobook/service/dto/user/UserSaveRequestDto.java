package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String userName;
    private PlatformType platformType;

    public User toEntity(){
        return User.builder()
                .email(email)
                .password(password)
                .userName(userName)
                .platformType(platformType)
                .build();
    }
}
