package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
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
public class GenreService {

    private final GenreRepository genreRepository;

    public List<GenreResponseDto> getAllGenres() {
        return genreRepository.findAll().stream()
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());
    }
}
