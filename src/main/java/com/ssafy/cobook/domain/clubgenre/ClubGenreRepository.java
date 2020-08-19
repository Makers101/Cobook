package com.ssafy.cobook.domain.clubgenre;

import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.service.dto.club.ClubBySearchResDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubGenreRepository extends JpaRepository<ClubGenre, Long> {

    List<ClubGenre> findAllByGenre(Genre genre);
}
