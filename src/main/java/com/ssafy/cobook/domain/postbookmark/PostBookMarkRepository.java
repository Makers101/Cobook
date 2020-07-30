package com.ssafy.cobook.domain.postbookmark;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostBookMarkRepository extends JpaRepository<PostBookMark, Long> {

    Optional<PostBookMark> findByUserAndPost(User user, Post post);

    List<PostBookMark> findAllByPost(Post post);
}
