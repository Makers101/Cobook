package com.ssafy.cobook.service.dto.user;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserResponseIdDto {
    private Long userId;

    public UserResponseIdDto(Long userId){
        this.userId = userId;
    }
}
