package com.ssafy.cobook.domain.clubmember;

import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClubMemberRepository extends JpaRepository<ClubMember, Long> {
    List<ClubMember> findAllByUser(User user);
}
