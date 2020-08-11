package com.ssafy.cobook.domain.post;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.service.dto.post.PostBySearchResDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findByClub(Club club);

    Optional<Post> findByUser(User user);

    Optional<Post> findByUserAndBook(User user, Book book);

    List<Post> findAllByUser(User user);

    List<Post> findAllByUserAndBook(User user, Book book);

    List<Post> findAllByBook(Book book);

//    @Query("select p from Post p " +
//            "join fetch p.club join fetch p.book join fetch p.bookMarks join fetch p.postLikes join fetch p.tags")
//    List<Post> findAllFetch();

    @Query("SELECT p FROM Post p where p.onelineReview like %:keyword% OR p.review like %:keyword%")
    List<PostBySearchResDto> findByKeyword(@Param("keyword") String keyword);
}
