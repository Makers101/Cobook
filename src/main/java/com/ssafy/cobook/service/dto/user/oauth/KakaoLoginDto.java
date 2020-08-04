package com.ssafy.cobook.service.dto.user.oauth;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Collections;

@Getter
@NoArgsConstructor
public class KakaoLoginDto {
    private String nickname;
    private String email;
    private String platformType;

    public User toEntity(){

        return User.builder()
                .email(email)
                .nickName(nickname)
                .platformType(PlatformType.valueOf("KAKAO"))
                .roles(Collections.singletonList("USER_ROLE"))
                .build();
    }
}
