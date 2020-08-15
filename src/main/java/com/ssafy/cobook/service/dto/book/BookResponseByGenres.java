package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.genre.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookResponseByGenres {

    private Long id;
    private String author;
    private String bookImg;
    private String contents;
    private String genre;
    private String isbn;
    private LocalDateTime pubDate;
    private String publisher;
    private String title;
    private String translators;
    private String url;

    public BookResponseByGenres(Book book, Genre genre) {
        this.id = book.getId();
        this.author = book.getAuthor();
        this.bookImg = book.getBookImg();
        this.contents = book.getContent();
        this.genre = genre.getGenreName();
        this.isbn = book.getIsbn();
        this.pubDate = book.getPubDate();
        this.publisher = book.getPublisher();
        this.title = book.getTitle();
        this.translators = book.getTranslators();
        this.url = book.getUrl();
    }
}
