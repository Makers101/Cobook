package com.ssafy.cobook.service.dto.question;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResDto {

    private Long questionId;
    private String question;

    public QuestionResDto(Long questionId, String question) {
        this.questionId = questionId;
        this.question = question;
    }
}
