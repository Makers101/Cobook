package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String nickName;
    private PlatformType platformType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public User toEntity(){

        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .platformType(platformType)
                .roles(Collections.singletonList("USER_ROLE"))
                .build();
    }
}
