package com.ssafy.cobook.service.dto.postcomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsReqDto {

    private String contents;
}
