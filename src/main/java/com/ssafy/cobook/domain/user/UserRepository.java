package com.ssafy.cobook.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);

    @Modifying
    @Query("UPDATE User u SET u.accept = :accept WHERE u.id= :userId")
    void updateAccept(@Param("userId") Long userId, @Param("accept") boolean accept);

    @Modifying
    @Query("UPDATE User u SET u.password = :password WHERE u.id= :userId")
    void updatePassword(@Param("userId") Long userId, @Param("password") String password);

}
