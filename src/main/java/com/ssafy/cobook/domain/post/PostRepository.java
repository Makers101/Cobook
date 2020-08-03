package com.ssafy.cobook.domain.post;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByClub(Club club);

    Optional<Post> findByUser(User user);

    Optional<Post> findByUserAndBook(User user, Book book);

    List<Post> findAllByUser(User user);

    List<Post> findAllByUserAndBook(User user, Book book);

    @Query("select p from Post p " +
            "join fetch p.club join fetch p.book join fetch p.bookMarks join fetch p.postLikes join fetch p.tags")
    List<Post> findAll();
}
