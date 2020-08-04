package com.ssafy.cobook.domain.clubeventquestion;

import com.ssafy.cobook.domain.clubevent.ClubEvent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_event_question_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_event_id")
    private ClubEvent clubEvent;

    private String question;

    public ClubEventQuestion(ClubEvent clubEvent, String question) {
        this.clubEvent = clubEvent;
        this.question = question;
    }
}
