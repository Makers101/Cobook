package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class OAuth2LoginDto {
    private String nickname;
    private String email;
    private String platformType;

    public User toEntity(String platformType){

        return User.builder()
                .email(email)
                .nickName(nickname)
                .accept(true)
                .platformType(PlatformType.valueOf(platformType))
                .roles(Collections.singletonList("USER_ROLE"))
                .build();
    }
}
