package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class BookBySearchResDto {
    private Long id;
    private String author;
    private String bookImg;
    private String contents;
    private String genre;
    private String isbn;
    private String publisher;
    private String title;
    private String translators;
    private String url;
    private LocalDateTime pubDate;

    public BookBySearchResDto(Book book){
        this.author = book.getAuthor();
        this.bookImg = book.getBookImg();
        this.contents = book.getContent();
        this.genre = book.getGenre().getGenreName();
        this.id = book.getId();
        this.isbn = book.getIsbn();
        this.pubDate = book.getPubDate();
        this.publisher = book.getPublisher();
        this.title = book.getTitle();
        this.translators = book.getTranslators();
        this.url = book.getUrl();
    }
}
