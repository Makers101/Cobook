package com.ssafy.cobook.domain.clubeventmember;

import com.ssafy.cobook.domain.clubevent.ClubEvent;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubEventMemberRepository extends JpaRepository<ClubEventMember, Long> {
    Optional<ClubEventMember> findByUserAndClubEvent(User user, ClubEvent clubEvent);
    List<ClubEventMember> findAllByUser(User user);
}
