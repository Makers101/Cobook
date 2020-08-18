package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.book.BookResponseByGenres;
import com.ssafy.cobook.service.dto.book.BookResponseDto;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public List<GenreResponseDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<BookResponseDto> getBooks(String genreName) {
        Genre genre = genreRepository.findByGenreName(genreName)
                .orElseThrow(()->new BaseException(ErrorCode.INVALID_GENRE));
        List<BookResponseDto> dtos = bookRepository.findAllByGenre(genre).stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());
        Collections.shuffle(dtos);
        return dtos;
    }
}
