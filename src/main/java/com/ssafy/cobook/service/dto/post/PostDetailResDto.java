package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.service.dto.posttag.PostTagDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResDto {

    private Long postId;
    private Long userId;
    private Long clubId;
    private String onelineReview;
    private String review;
    private Integer rank;
    private Boolean open;
    private Boolean isClub;
    private String username;
    private Long bookId;
    private String bookImage;
    private String title;
    private String author;
    private String translators;
    private String publisher;
    private LocalDateTime publishDate;
    private Integer likesCount;
    private Integer commentsCount;
    private List<PostTagDto> tags;

    public PostDetailResDto(Post post, List<PostTagDto> tags) {
        this.postId = post.getId();
        if (post.ofClub()) {
            this.clubId = post.getClub().getId();
        } else {
            this.userId = post.getUser().getId();
        }
        this.onelineReview = post.getOnelineReview();
        this.review = post.getReview();
        this.rank = post.getRank();
        this.open = post.getOpen();
        this.isClub = post.getIsClub();
        this.username = post.getUser().getUserName();
        this.bookId = post.getBook().getId();
        this.bookImage = post.getBook().getBookImg();
        this.title = post.getBook().getTitle();
        this.author = post.getBook().getAuthor();
        this.translators = post.getBook().getTranslators();
        this.publisher = post.getBook().getPublisher();
        this.publishDate = post.getBook().getPublishDates();
        this.likesCount = post.getPostLikes().size();
        this.commentsCount = post.getComments().size();
        this.tags = tags;
    }
}
