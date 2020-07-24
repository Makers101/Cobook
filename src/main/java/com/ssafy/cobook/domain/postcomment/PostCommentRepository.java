package com.ssafy.cobook.domain.postcomment;

import com.ssafy.cobook.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostCommentRepository extends JpaRepository<PostComment, Long> {

    List<PostComment> findAllByPost(Post post);
}
