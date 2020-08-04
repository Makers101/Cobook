package com.ssafy.cobook.domain.club;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ClubRepository extends JpaRepository<Club, Long> {

    Optional<Club> findByName(String name);
    Optional<Club> findById(Long clubId);
//
//    @Query("select c from Club c join fetch c.members join fetch c.followList join fetch c.genres")
//    List<Club> findAllFetch();
}