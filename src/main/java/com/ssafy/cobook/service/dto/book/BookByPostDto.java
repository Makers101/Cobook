package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookByPostDto {

    private Long id;
    private String title;
    private String bookImg;

    public BookByPostDto(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.bookImg = book.getBookImg();
    }
}
