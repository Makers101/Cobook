package com.ssafy.cobook.domain.clubevent;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.clubeventmember.ClubEventMember;
import com.ssafy.cobook.domain.clubeventquestion.ClubEventQuestion;
import com.ssafy.cobook.service.dto.clubevent.ClubEventUpdateReqDto;
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
public class ClubEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_event_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "clubEvent", cascade = CascadeType.ALL)
    private List<ClubEventQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "clubEvent", cascade = CascadeType.ALL)
    private List<ClubEventMember> members = new ArrayList<>();

    private String title;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private Boolean closed;

    @Builder
    public ClubEvent(String title, LocalDateTime dateTime, String place, String description, Boolean closed) {
        this.title = title;
        this.dateTime = dateTime;
        this.place = place;
        this.description = description;
        this.closed = closed;
    }

    public void ofClub(Club club) {
        this.club = club;
    }

    public void ofBook(Book book) {
        this.book = book;
    }

    public void enrollQuestion(List<ClubEventQuestion> questions) {
        this.questions = questions;
    }

    public void addMember(ClubEventMember readingMember) {
        if (this.members.contains(readingMember)) {
            this.members.remove(readingMember);
            return;
        }
        this.members.add(readingMember);
    }

    public void removeMember(ClubEventMember delete) {
        this.members.remove(delete);
    }

    public void delete() {
        for (ClubEventMember member : members) {
            member.removeUser();
        }
        book.removeEvent(this);
        this.questions = null;
    }

    public void updateInfo(ClubEventUpdateReqDto reqDto) {
        this.title = reqDto.getName();
        this.dateTime = reqDto.getDatetime();
        this.place = reqDto.getPlace();
        this.description = reqDto.getDescription();
    }

    public void setQuestions(List<ClubEventQuestion> questions) {
        this.questions = questions;
    }

    public void changeBook(Book book) {
        this.book.removeEvent(this);
        this.book = book;
        this.book.enrollReading(this);
    }
}