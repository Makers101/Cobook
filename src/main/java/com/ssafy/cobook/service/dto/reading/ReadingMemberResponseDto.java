package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.readingmember.ReadingMember;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingMemberResponseDto {

    private Long id;
    private String nickName;
    private String profileImg;

    public ReadingMemberResponseDto(ReadingMember readingMember) {
        this.id = readingMember.getUser().getId();
        this.nickName = readingMember.getUser().getNickName();
        if (readingMember.getUser().getProfileImg() != null) {
            this.profileImg = "http://i3a111.p.ssafy.io:8080/api/profile/images/" + this.id;
        }
    }
}
