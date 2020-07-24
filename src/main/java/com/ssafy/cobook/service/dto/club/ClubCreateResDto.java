package com.ssafy.cobook.service.dto.club;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubCreateResDto {

    private Long clubId;

    public ClubCreateResDto(Long id) {
        this.clubId = id;
    }
}
