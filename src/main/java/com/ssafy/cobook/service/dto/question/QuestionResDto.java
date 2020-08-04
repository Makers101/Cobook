package com.ssafy.cobook.service.dto.question;

import com.ssafy.cobook.domain.clubeventquestion.ClubEventQuestion;
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

    public QuestionResDto(ClubEventQuestion question) {
        this.id = question.getId();
        this.question = question.getQuestion();
    }
}
