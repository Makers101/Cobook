package com.ssafy.cobook.domain.reading;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.readingbook.ReadingBook;
import com.ssafy.cobook.domain.readingmember.ReadingMember;
import com.ssafy.cobook.domain.readingquestion.ReadingQuestion;
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
public class Reading {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reading_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToMany(mappedBy = "reading", cascade = CascadeType.ALL)
    private List<ReadingBook> readingBooks = new ArrayList<>();

    @OneToMany(mappedBy = "reading", cascade = CascadeType.ALL)
    private List<ReadingQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "reading", cascade = CascadeType.ALL)
    private List<ReadingMember> members = new ArrayList<>();

    private String title;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private String oneLineDescription;
}