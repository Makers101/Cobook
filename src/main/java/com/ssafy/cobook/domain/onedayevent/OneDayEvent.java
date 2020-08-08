package com.ssafy.cobook.domain.onedayevent;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMember;
import com.ssafy.cobook.domain.onedayeventquestion.OneDayEventQuestion;
import com.ssafy.cobook.service.dto.onedayevent.OneDayEventUpdateReqDto;
import lombok.AccessLevel;
import lombok.Builder;
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
    private Boolean closed;
    private Integer capacity;

    @Builder
    public OneDayEvent(String title, LocalDateTime dateTime, String place, String description, Boolean closed, Integer capacity) {
        this.title = title;
        this.dateTime = dateTime;
        this.place = place;
        this.description = description;
        this.closed = closed;
        this.capacity = capacity;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void addMember(OneDayEventMember member) {
        this.members.add(member);
    }

    public void setQuestions(List<OneDayEventQuestion> questions) {
        this.questions = questions;
    }

    public void removeMember(OneDayEventMember member) {
        this.members.remove(member);
    }

    public void updateInfo(OneDayEventUpdateReqDto requestDto) {
        this.title = requestDto.getName();
        this.dateTime = requestDto.getDatetime();
        this.place = requestDto.getPlace();
        this.description = requestDto.getDescription();
        this.capacity = requestDto.getCapacity();
    }

    public void chageBook(Book book) {
        this.book.removeEvent(this);
        this.book = book;
        book.addEvent(this);
    }

    public void isEnd(LocalDateTime now) {
        if (this.dateTime.isBefore(now)) {
            this.closed = true;
        }
    }
}
