package com.ssafy.cobook.service.dto.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileFollowUserDto {
    Long id;
    Long fromUserId;
    Long toUserId;
    Boolean isClub;
}
