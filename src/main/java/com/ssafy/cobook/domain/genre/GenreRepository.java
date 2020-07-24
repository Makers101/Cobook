package com.ssafy.cobook.domain.genre;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    Optional<Genre> findByGenre(String genre);
}
