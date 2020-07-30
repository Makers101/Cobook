package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.genre.Genre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookResponseDto {

    private Long id;
    private String genre;
    private String isbn;
    private String bookImg;
    private String title;
    private String author;
    private String translators;
    private String publisher;
    private String contents;
    private String url;
    private LocalDateTime pubDate;

    public BookResponseDto(Book book) {
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.bookImg = book.getBookImg();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.translators = book.getTranslators();
        this.publisher = book.getPublisher();
        this.contents = book.getContent();
        this.url = book.getUrl();
        this.pubDate = book.getPubDate();
        this.genre = book.getGenre().getGenreName();
    }
}
