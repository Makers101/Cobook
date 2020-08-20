package com.ssafy.cobook.domain.genre;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query("SELECT g from Genre g where g.genreName like %:genre%")
    Optional<Genre> findByGenreName(@Param("genre") String genre);

    @Query("SELECT g from Genre g where g.genreName like %:genre%")
    List<Genre> findAllByGenreName(@Param("genre") String genre);
}
