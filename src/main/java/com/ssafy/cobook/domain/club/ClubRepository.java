package com.ssafy.cobook.domain.club;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByName(String name);
    Optional<Club> findById(Long clubId);
    List<Club> findAllById(Long clubId);
}