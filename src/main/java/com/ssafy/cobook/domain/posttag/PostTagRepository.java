package com.ssafy.cobook.domain.posttag;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostTagRepository extends JpaRepository<PostTag, Long> {
    Optional<PostTag> findByPostAndTag(Post post, Tag tag);

    List<PostTag> findAllByPost(Post post);
}
