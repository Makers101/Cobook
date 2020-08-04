package com.ssafy.cobook.domain.clubevent;

import com.ssafy.cobook.domain.club.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ClubEventRepository extends JpaRepository<ClubEvent, Long> {
    List<ClubEvent> findAllByClub(Club club);
}