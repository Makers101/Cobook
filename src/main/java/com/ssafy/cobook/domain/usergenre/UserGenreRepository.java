package com.ssafy.cobook.domain.usergenre;

import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserGenreRepository extends JpaRepository<UserGenre, Long> {
    Optional<UserGenre> findByUserAndGenre(User user, Genre genre);
}
