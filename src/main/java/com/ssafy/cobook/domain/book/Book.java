package com.ssafy.cobook.domain.book;

import com.ssafy.cobook.domain.booklike.BookLike;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.meetup.MeetUp;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.readingbook.ReadingBook;
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
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long id;

    private String bookImg;
    private String title;
    private String author;
    private String translators;
    private String publisher;
    private String contents;
    private String url;
    private LocalDateTime publishDates;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ReadingBook> readingBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<MeetUp> meetUps = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;
}
