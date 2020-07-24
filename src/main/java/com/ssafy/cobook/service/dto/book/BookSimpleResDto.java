package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookSimpleResDto {

    private Long bookId;
    private String bookTitle;
    private String bookImage;

    public BookSimpleResDto(Book book) {
        this.bookId = book.getId();
        this.bookTitle = book.getTitle();
        this.bookImage = book.getBookImg();
    }
}
