package com.ssafy.cobook.service.dto.profile;

import com.ssafy.cobook.domain.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowQueryDto {
    User toUser;
    User fromUser;

    public void FollowQueryDto(User toUser, User fromUser){
        this.toUser = toUser;
        this.fromUser = fromUser;
    }
}
