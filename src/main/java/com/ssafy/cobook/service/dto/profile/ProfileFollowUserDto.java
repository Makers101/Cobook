package com.ssafy.cobook.service.dto.profile;

import com.ssafy.cobook.service.dto.user.UserFollowResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileFollowUserDto {
    UserFollowResDto toUser; // 아이디, 닉네임
    UserFollowResDto fromUser; // 아이디, 닉네임
    Boolean isClub;
    Boolean isFollow;

    public ProfileFollowUserDto(UserFollowResDto toUser, UserFollowResDto fromUser, Boolean isClub){
        this.toUser = toUser;
        this.fromUser = fromUser;
        this.isClub = isClub;
    }
}
