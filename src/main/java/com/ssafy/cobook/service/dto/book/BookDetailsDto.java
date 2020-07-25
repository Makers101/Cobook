package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDetailsDto {

    private Long id;
    private String title;
    private String bookImg;
    private String author;
    private String translators;
    private String publisher;
    private String contents;
    private String url;
    private String pubDate;

    public BookDetailsDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.bookImg = book.getBookImg();
        this.author = book.getAuthor();
        this.translators = book.getTranslators();
        this.publisher = book.getPublisher();
        this.contents = book.getContents();
        this.url = book.getUrl();
        this.pubDate = book.getPublishDates().toString();
    }
}
