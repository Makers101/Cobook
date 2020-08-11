package com.ssafy.cobook.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdatePwdDto {

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "/^[a-zA-Z0-9]*$/", message = "영문자, 숫자를 포함한 8자리 이상의 비밀번호를 입력하세요.")
    @Length(min = 8, max = 128)
    private String password;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "/^[a-zA-Z0-9]*$/", message = "영문자, 숫자를 포함한 8자리 이상의 비밀번호를 입력하세요.")
    @Length(min = 8, max = 128)
    private String passwordConfirm;

    private String jwt;
}
