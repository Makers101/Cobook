package com.ssafy.cobook.service.dto.postlike;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostLikeReqDto {

    private Long userId;
    private Long postId;
}
