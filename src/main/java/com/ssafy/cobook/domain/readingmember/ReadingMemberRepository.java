package com.ssafy.cobook.domain.readingmember;

import com.ssafy.cobook.domain.reading.Reading;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReadingMemberRepository extends JpaRepository<ReadingMember, Long> {
    Optional<ReadingMember> findByUserAndReading(User user, Reading reading);
    List<ReadingMember> findAllByUser(User user);
}
