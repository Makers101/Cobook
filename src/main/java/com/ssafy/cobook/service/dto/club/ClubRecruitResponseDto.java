package com.ssafy.cobook.service.dto.club;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubRecruitResponseDto {

    private boolean recruit;

    public ClubRecruitResponseDto(Boolean recruit) {
        this.recruit = recruit;
    }
}
