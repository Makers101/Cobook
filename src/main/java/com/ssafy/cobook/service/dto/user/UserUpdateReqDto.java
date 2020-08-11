package com.ssafy.cobook.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateReqDto {

    @NotEmpty(message = "닉네임을 입력해주세요.")
    @Length(min = 2, max = 128)
    private String nickName;
    @Length(max = 128)
    private String description;
    private List<Long> genres;
}
