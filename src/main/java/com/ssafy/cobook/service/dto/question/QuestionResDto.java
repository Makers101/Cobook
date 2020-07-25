package com.ssafy.cobook.service.dto.question;

import com.ssafy.cobook.domain.readingquestion.ReadingQuestion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionResDto {

    private Long id;
    private String question;

    public QuestionResDto(Long questionId, String question) {
        this.id = questionId;
        this.question = question;
    }

    public QuestionResDto(ReadingQuestion question) {
        this.id = question.getId();
        this.question = question.getQuestion();
    }
}
