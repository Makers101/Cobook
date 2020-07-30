package com.ssafy.cobook.domain.clubmember;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findAllByUser(User user);
    Optional<ClubMember> findByUserAndClub(User user, Club club);

    List<ClubMember> findAllByClub(Club club);
}
