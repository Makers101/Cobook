package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Collections;

@Getter
@NoArgsConstructor
public class OAuth2LoginDto {

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Length(min = 2, max = 128)
    private String nickName;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;

    @NotEmpty
    @Length(max = 128)
    private String platformType;

    public User toEntity(String platformType) {

        return User.builder()
                .email(email)
                .nickName(nickName)
                .accept(true)
                .platformType(PlatformType.valueOf(platformType))
                .roles(Collections.singletonList("USER_ROLE"))
                .build();
    }
}
