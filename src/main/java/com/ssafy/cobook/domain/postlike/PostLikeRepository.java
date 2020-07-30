package com.ssafy.cobook.domain.postlike;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    Optional<PostLike> findByUserAndPost(User user, Post post);

    List<PostLike> findAllByPost(Post post);
}
