package com.ssafy.cobook.service.dto.club;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEnrollReqDto {

    private Long userId;
    private Long clubId;
}
