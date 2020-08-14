package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.book.BookDetailResponseDto;
import com.ssafy.cobook.service.dto.book.BookResponseByGenres;
import com.ssafy.cobook.service.dto.book.BookResponseDto;
import com.ssafy.cobook.service.dto.post.PostByMembersResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final PostRepository postRepository;
    private final GenreRepository genreRepository;

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());
    }

    public BookDetailResponseDto getDetials(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_BOOK));
        List<PostByMembersResDto> posts = postRepository.findAllByBook(book).stream()
                .map(PostByMembersResDto::new)
                .collect(Collectors.toList());
        return new BookDetailResponseDto(book, posts);
    }

    public List<BookResponseByGenres> findBookByGenre(Long genreId) {
        Genre genre = genreRepository.findById(genreId)
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_GENRE));
        return bookRepository.findAllByGenre(genre).stream()
                .limit(5)
                .map(b -> new BookResponseByGenres(b, genre))
                .collect(Collectors.toList());
    }
}
