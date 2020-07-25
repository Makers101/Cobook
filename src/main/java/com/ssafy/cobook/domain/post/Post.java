package com.ssafy.cobook.domain.post;

import com.ssafy.cobook.domain.baseEntity.BaseEntity;
import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.postbookmark.PostBookMark;
import com.ssafy.cobook.domain.postcomment.PostComment;
import com.ssafy.cobook.domain.postlike.PostLike;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private String onelineReview;
    private String review;
    private Integer rank;
    private Boolean open;
    private Boolean isClub;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostBookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    private List<PostTag> tags = new ArrayList<>();

    @Builder
    public Post(String onelineReview, String review, Integer rank, Boolean open, Boolean isClub) {
        this.onelineReview = onelineReview;
        this.review = review;
        this.rank = rank;
        this.open = open;
        this.isClub = isClub;
    }

    public void of(User user) {
        this.user = user;
    }

    public void of(Club club) {
        this.club = club;
    }

    public void ofBook(Book book) {
        this.book = book;
    }

    public void setTags(List<PostTag> tags) {
        this.tags = tags;
        for( PostTag p : tags) {
            p.ofPost(this);
        }
    }

    public boolean ofClub() {
        return this.isClub;
    }

    public void addComments(PostComment postComment) {
        this.comments.add(postComment);
    }
}
