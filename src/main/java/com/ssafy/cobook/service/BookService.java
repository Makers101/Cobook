package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.service.dto.book.BookResponseDto;
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

    public List<BookResponseDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(BookResponseDto::new)
                .collect(Collectors.toList());
    }
}
