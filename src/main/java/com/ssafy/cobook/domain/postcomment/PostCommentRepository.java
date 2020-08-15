package com.ssafy.cobook.domain.postcomment;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findAllByPost(Post post);
    Optional<PostComment> findByUserAndPost(User user, Post post);
    Optional<PostComment> findById(Long id);

    @Query("SELECT c FROM PostComment c ORDER BY c.id DESC")
    List<PostComment> findByLastComment(User user, Post post);
}
