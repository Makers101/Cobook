package com.ssafy.cobook.domain.onedayevent;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMember;
import com.ssafy.cobook.domain.onedayeventquestion.OneDayEventQuestion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_day_event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "oneDayEvent", cascade = CascadeType.ALL)
    private List<OneDayEventQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "oneDayEvent", cascade = CascadeType.ALL)
    private List<OneDayEventMember> members = new ArrayList<>();

    private String title;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private String onelineDescription;
    private Boolean closed;
    private Integer capacity;
}
