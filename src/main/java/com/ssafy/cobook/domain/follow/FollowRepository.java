package com.ssafy.cobook.domain.follow;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFromUser(User user);

    List<Follow> findAllByToUser(User user);

    @Query("SELECT f FROM Follow AS f WHERE f.fromUser.id= ?1 AND f.toUser.id= ?2")
    Optional<Follow> findByToUserAndFromUser(Long fromUserId, Long toUserId);


    @Modifying
    @Query("DELETE FROM Follow WHERE fromUser.id= :fromUser AND toUser.id= :toUser")
    void deleteByUser(@Param("fromUser")Long fromUser, @Param("toUser") Long toUser);

    Optional<Follow> findByFromUserAndClub(User user, Club club);

    List<Follow> findAllByClub(Club club);

    @Modifying
    @Query("DELETE FROM Follow WHERE fromUser.id= :userId AND club.id= :clubId")
    void deleteByUserAndClub(@Param("userId")Long userId, @Param("clubId") Long clubId);
}
