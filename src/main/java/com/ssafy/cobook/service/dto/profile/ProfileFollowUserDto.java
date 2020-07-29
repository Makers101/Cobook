package com.ssafy.cobook.service.dto.profile;

import com.ssafy.cobook.service.dto.user.UserFollowResDto;
import com.ssafy.cobook.service.dto.user.UserSimpleResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProfileFollowUserDto {
    UserFollowResDto toUser; // 아이디, 닉네임
    UserFollowResDto fromUser; // 아이디, 닉네임
    Boolean isClub;
    Boolean isFollow;
}
