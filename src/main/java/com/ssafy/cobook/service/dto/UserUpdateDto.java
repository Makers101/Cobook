package com.ssafy.cobook.service.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserUpdateDto {
    private String email;
    private String password;
    private String nickName;
    private String description;
    private String profileImg;

}
