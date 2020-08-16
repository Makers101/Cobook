package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.GenreService;
import com.ssafy.cobook.service.dto.book.BookResponseByGenres;
import com.ssafy.cobook.service.dto.book.BookResponseDto;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/genres")
@RequiredArgsConstructor
public class GenreController {

    private static final Logger logger = LoggerFactory.getLogger(GenreController.class);

    private final GenreService genreService;

    @ApiOperation(value = "장르 전체 조회")
    @GetMapping
    public ResponseEntity<List<GenreResponseDto>> getAllGenres() {
        return ResponseEntity.status(HttpStatus.OK).body(genreService.getAllGenres());
    }

    @ApiOperation(value = "장르의 책 조회")
    @GetMapping("/{genreId}/books")
    public ResponseEntity<List<BookResponseDto>> getBooksByGenre(@PathVariable("genreId") Long genreId) {
        return ResponseEntity.status(HttpStatus.OK).body(genreService.getBooks(genreId));
    }
}
