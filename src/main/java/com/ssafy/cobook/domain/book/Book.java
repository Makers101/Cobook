package com.ssafy.cobook.domain.book;

import com.ssafy.cobook.domain.booklike.BookLike;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.clubevent.ClubEvent;
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

    private String isbn;
    private String bookImg;
    private String title;
    private String author;
    private String translators;
    private String publisher;
    private String content;
    private String url;
    private LocalDateTime pubDate;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<BookLike> likes = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<ClubEvent> readingBooks = new ArrayList<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<OneDayEvent> oneDayEvents = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genre_id")
    private Genre genre;

    public void enrollReading(ClubEvent clubEvent) {
        this.readingBooks.add(clubEvent);
    }

    public void connetPost(Post post) {
        this.posts.add(post);
    }

    public void removeEvent(ClubEvent clubEvent) {
        this.readingBooks.remove(clubEvent);
    }

    public void addEvent(OneDayEvent oneDayEvent) {
        this.oneDayEvents.add(oneDayEvent);
    }

    public void removeEvent(OneDayEvent oneDayEvent) {
        this.oneDayEvents.remove(oneDayEvent);
    }
}
