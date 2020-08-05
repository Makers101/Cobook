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

    @Query("select f from Follow as f join fetch f.toUser")
    List<Follow> findAllByFromUser(User fromUser);

    @Query("SELECT f FROM Follow AS f WHERE f.fromUser= ?1 AND f.toUser= ?2")
    Optional<Follow> findByToUser(User fromUser, User toUser);

    @Query("SELECT f FROM Follow AS f WHERE f.fromUser.id= ?1 AND f.toUser.id IN (SELECT g.toUser.id FROM Follow AS g WHERE g.fromUser.id= ?2)")
    List<Follow> findAllByFollowing(Long toUser, Long fromUser);

    @Query("SELECT f FROM Follow AS f WHERE f.fromUser.id= ?1 AND f.toUser.id NOT IN (SELECT g.toUser.id FROM Follow AS g WHERE g.fromUser.id= ?2)")
    List<Follow> findAllByNotFollowing(Long toUser, Long fromUser);

    @Query("SELECT f FROM Follow AS f WHERE f.toUser.id= ?1 AND f.fromUser.id IN (SELECT g.toUser.id FROM Follow AS g WHERE g.fromUser.id= ?2)")
    List<Follow> findAllByFollower(Long toUser, Long fromUser);

    @Query("SELECT f FROM Follow AS f WHERE f.toUser.id= ?1 AND f.fromUser.id NOT IN (SELECT g.toUser.id FROM Follow AS g WHERE g.fromUser.id= ?2)")
    List<Follow> findAllByNotFollower(Long toUser, Long fromUser);

    @Modifying
    @Query("DELETE FROM Follow WHERE fromUser.id= :fromUser AND toUser.id= :toUser")
    void deleteByUser(@Param("fromUser")Long fromUser, @Param("toUser") Long toUser);

    Optional<Follow> findByFromUserAndClub(User user, Club club);

    List<Follow> findAllByClub(Club club);

    @Modifying
    @Query("DELETE FROM Follow WHERE fromUser.id= :userId AND club.id= :clubId")
    void deleteByUserAndClub(@Param("userId")Long userId, @Param("clubId") Long clubId);
}
