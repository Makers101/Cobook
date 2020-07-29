package com.ssafy.cobook.service.dto.profile;

import com.ssafy.cobook.service.dto.user.UserByFollowDto;

import java.util.List;

public class FollowResponseData {
    List<UserByFollowDto> following; // 내가 누른 사람들
    List<UserByFollowDto> follower; // 나를 누른 사람들

    public FollowResponseData(List<UserByFollowDto> following, List<UserByFollowDto> follower) {
        this.following = following;
        this.follower = follower;
    }
}
