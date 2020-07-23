package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserSaveRequestDto {

    private String email;
    private String password;
    private String userName;
    private PlatformType platformType;

    public User toEntity(){
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(password);
        return User.builder()
                .email(email)
                .password(encodePassword)
                .userName(userName)
                .platformType(platformType)
                .build();
    }
}
