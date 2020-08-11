package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BookByFeedDto {
    private Long id;
    private String title;
    private String bookImg;
    private String author;
    private String publisher;
    private String pubDate;

    public BookByFeedDto(Book book){
        this.id = book.getId();
        this.title = book.getTitle();
        this.bookImg = book.getBookImg();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.pubDate = book.getPubDate().toString();
    }
}
