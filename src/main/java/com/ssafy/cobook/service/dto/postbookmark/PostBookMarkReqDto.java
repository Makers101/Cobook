package com.ssafy.cobook.service.dto.postbookmark;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostBookMarkReqDto {

    private Long postId;
}
