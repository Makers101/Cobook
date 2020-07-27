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
    private LocalDateTime publishDates;

    public BookResponseDto(Book book) {
        id = book.getId();
        isbn = book.getIsbn();
        bookImg = book.getBookImg();
        title = book.getTitle();
        author = book.getAuthor();
        translators = book.getTranslators();
        publisher = book.getPublisher();
        contents = book.getContents();
        url = book.getUrl();
        publishDates = book.getPublishDates();
        genre = book.getGenre().getGenreName();
    }
}
