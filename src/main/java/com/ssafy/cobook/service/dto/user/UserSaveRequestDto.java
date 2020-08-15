package com.ssafy.cobook.service.dto.user;

import com.ssafy.cobook.domain.user.PlatformType;
import com.ssafy.cobook.domain.user.User;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserSaveRequestDto {

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "이메일 형식이 올바르지 않습니다.")
    private String email;


    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=\\S+$).{8,20}")
    private String password;

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Length(min = 2, max = 128)
    private String nickName;

    private PlatformType platformType;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    public User toEntity() {

        return User.builder()
                .email(email)
                .password(password)
                .nickName(nickName)
                .platformType(PlatformType.valueOf("NONE"))
                .roles(Collections.singletonList("USER_ROLE"))
                .build();
    }
}
