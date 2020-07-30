package com.ssafy.cobook.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserUpdateReqDto {

    private String nickName;
    private String description;
    private List<Long> genres;
}
