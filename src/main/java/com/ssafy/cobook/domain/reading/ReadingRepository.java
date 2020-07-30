package com.ssafy.cobook.domain.reading;

import com.ssafy.cobook.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReadingRepository extends JpaRepository<Reading, Long> {
    List<Reading> findAllByClub(Club club);
}