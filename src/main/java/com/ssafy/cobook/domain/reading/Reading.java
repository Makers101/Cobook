package com.ssafy.cobook.domain.reading;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.readingmember.ReadingMember;
import com.ssafy.cobook.domain.readingquestion.ReadingQuestion;
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
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "reading", cascade = CascadeType.ALL)
    private List<ReadingQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "reading", cascade = CascadeType.ALL)
    private List<ReadingMember> members = new ArrayList<>();

    private String title;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private String oneLineDescription;
    private Boolean closed;

    @Builder
    public Reading(String title, LocalDateTime dateTime, String place, String description, String oneLineDescription, Boolean closed) {
        this.title = title;
        this.dateTime = dateTime;
        this.place = place;
        this.description = description;
        this.oneLineDescription = oneLineDescription;
        this.closed = closed;
    }

    public void ofClub(Club club) {
        this.club = club;
    }

    public void ofBook(Book book) {
        this.book = book;
    }

    public void enrollQuestion(List<ReadingQuestion> questions) {
        this.questions = questions;
    }

    public void addMember(ReadingMember readingMember) {
        if( this.members.contains(readingMember)) {
            this.members.remove(readingMember);
            return;
        }
        this.members.add(readingMember);
    }
}