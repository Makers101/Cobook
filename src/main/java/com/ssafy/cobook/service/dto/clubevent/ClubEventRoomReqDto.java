package com.ssafy.cobook.service.dto.clubevent;

import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventRoomReqDto {

    @NotNull
    private String url;
}
