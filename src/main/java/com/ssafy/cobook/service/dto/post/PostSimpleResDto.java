package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostSimpleResDto {

    private Long postId;
    private String onelineReview;
    private Integer rank;
    private Integer postLikesCount;
    private Integer postCommentsCount;
    private List<String> postTags;
    private Long userId;
    private String nickName;
    private Long bookId;
    private String bookImage;
    private String bookTitle;

    public PostSimpleResDto(Post post) {
        this.postId = post.getId();
        this.onelineReview = post.getOnelineReview();
        this.rank = post.getRank();
        this.postLikesCount = post.getPostLikes().size();
        this.postCommentsCount = post.getComments().size();
        this.postTags = post.getTags().stream()
                .map(PostTag::getTag)
                .map(Tag::getTagName)
                .collect(Collectors.toList());
        this.userId = post.getUser().getId();
        this.nickName = post.getUser().getNickName();
        this.bookId = post.getBook().getId();
        this.bookImage = post.getBook().getBookImg();
        this.bookTitle = post.getBook().getTitle();
    }
}
