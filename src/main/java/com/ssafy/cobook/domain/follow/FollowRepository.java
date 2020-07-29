package com.ssafy.cobook.domain.follow;

import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    List<Follow> findAllByFromUser(User user);

    @Query("SELECT f FROM Follow AS f WHERE f.fromUser= ?1 AND f.toUser= ?2")
    Optional<Follow> findByToUser(User fromUser, User toUser);

    @Query(value = "SELECT * FROM Follow AS f1 WHERE f1.from_user_id= ?1 AND f1.to_user_id IN (SELECT f2.to_user_id FROM Follow AS f2 WHERE f2.from_user_id= ?2)", nativeQuery = true)
    List<Follow> findAllByFollowing(Long toUser, Long fromUser);

    @Query(value = "SELECT * FROM Follow AS f1 WHERE f1.from_user_id= ?1 AND f1.to_user_id NOT IN (SELECT f2.to_user_id FROM Follow AS f2 WHERE f2.from_user_id= ?2)", nativeQuery = true)
    List<Follow> findAllByNotFollowing(Long toUser, Long fromUser);

    @Query(value = "SELECT * FROM Follow AS f1 WHERE f1.to_user_id= ?1 AND f1.from_user_id IN (SELECT f2.to_user_id FROM Follow AS f2 WHERE f2.from_user_id= ?2)", nativeQuery = true)
    List<Follow> findAllByFollower(Long toUser, Long fromUser);

    @Query(value = "SELECT * FROM Follow AS f1 WHERE f1.to_user_id= ?1 AND f1.from_user_id NOT IN (SELECT f2.to_user_id FROM Follow AS f2 WHERE f2.from_user_id= ?2)", nativeQuery = true)
    List<Follow> findAllByNotFollower(Long toUser, Long fromUser);

    @Modifying
    @Query(value = "DELETE FROM Follow WHERE from_user_id= :fromUser AND to_user_id= :toUser", nativeQuery = true)
    void deleteByUser(@Param("fromUser")Long fromUser, @Param("toUser") Long toUser);
}
