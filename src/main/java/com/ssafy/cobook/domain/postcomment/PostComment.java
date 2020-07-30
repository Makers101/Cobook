package com.ssafy.cobook.domain.postcomment;

import com.ssafy.cobook.domain.baseEntity.BaseTimeEntity;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostComment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private String content;

    public PostComment(Post post, User user, String content) {
        this.post = post;
        this.user = user;
        this.content = content;
    }

    public void removeUser() {
        this.user.removeComment(this);
        this.user = null;
    }
}
