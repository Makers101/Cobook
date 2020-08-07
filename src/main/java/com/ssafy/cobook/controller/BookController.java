package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.BookService;
import com.ssafy.cobook.service.dto.book.BookDetailsDto;
import com.ssafy.cobook.service.dto.book.BookResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @ApiOperation(value = "책 데이터 전체 조회하기")
    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getAllBooks());
    }

    @ApiOperation(value = "책 상세조회")
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDetailResponseDto> getBookDetails(@PathVariable('bookId') final Long bookId) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.getDetials(bookId));
    }
}
