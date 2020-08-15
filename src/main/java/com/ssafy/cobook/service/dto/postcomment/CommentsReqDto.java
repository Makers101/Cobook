package com.ssafy.cobook.service.dto.postcomment;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentsReqDto {
    @Length(min = 1, max = 128)
    private String content;
}
