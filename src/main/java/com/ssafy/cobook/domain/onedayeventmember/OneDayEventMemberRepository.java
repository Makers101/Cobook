package com.ssafy.cobook.domain.onedayeventmember;

import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OneDayEventMemberRepository extends JpaRepository<OneDayEventMember, Long> {

    Optional<OneDayEventMember> findByUserAndOneDayEvent(User user, OneDayEvent event);
}
