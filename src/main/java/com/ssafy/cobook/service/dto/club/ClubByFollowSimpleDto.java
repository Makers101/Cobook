package com.ssafy.cobook.service.dto.club;

public class ClubByFollowSimpleDto {
    Long fromUser; // 구독자 번호
    boolean isFollow; //
    Long usersCount; // 구독자 수

    public ClubByFollowSimpleDto(Long fromUser, boolean isFollow, Long usersCount){
        this.fromUser = fromUser;
        this.isFollow = isFollow;
        this.usersCount = usersCount;
    }
}
