package com.ssafy.cobook.service.dto.book;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.service.dto.post.PostByMembersResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDetailResponseDto {

    private Long id;
    private String title;
    private String bookImg;
    private String author;
    private String translators;
    private String publisher;
    private String contents;
    private String url;
    private String pubDate;
    private List<PostByMembersResDto> posts;

    public BookDetailResponseDto(Book book, List<PostByMembersResDto> posts) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.bookImg = book.getBookImg();
        this.author = book.getAuthor();
        this.translators = book.getTranslators();
        this.publisher = book.getPublisher();
        this.contents = book.getContent();
        this.url = book.getUrl();
        this.pubDate = book.getPubDate().toString();
        this.posts = posts;
    }
}
