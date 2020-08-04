package com.ssafy.cobook.domain.onedayeventquestion;

import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_day_event_question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_event_id")
    private OneDayEvent oneDayEvent;

    private String question;

    public OneDayEventQuestion(OneDayEvent oneDayEvent, String question) {
        this.oneDayEvent = oneDayEvent;
        this.question = question;
    }
}
