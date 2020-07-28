package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.domain.tag.Tag;
import com.ssafy.cobook.service.dto.posttag.PostTagDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResDto {

    private Long postId;
    private String bookImage;
    private String onelineReview;
    private String userName;
    private String bookTitle;
    private Integer likeCount;
    private Integer commentsCount;
    private List<PostTagDto> tags;

    public static PostResDto ofClub(Post post) {
        return PostResDto.builder()
                .postId(post.getId())
                .bookImage(post.getBook().getBookImg())
                .bookTitle(post.getBook().getTitle())
                .onelineReview(post.getOnelineReview())
                .userName(post.getClub().getName())
//                .tags(post.getTags())
                .commentsCount(post.getComments().size())
                .likeCount(post.getPostLikes().size())
                .build();
    }

    public static PostResDto ofUser(Post post) {
        return PostResDto.builder()
                .postId(post.getId())
                .bookImage(post.getBook().getBookImg())
                .bookTitle(post.getBook().getTitle())
                .onelineReview(post.getOnelineReview())
                .userName(post.getUser().getNickName())
//                .tags(post.getTags())
                .commentsCount(post.getComments().size())
                .likeCount(post.getPostLikes().size())
                .build();
    }

    @Builder
    public PostResDto(Long postId, String bookImage, String onelineReview, String userName, String bookTitle, Integer likeCount, Integer commentsCount, List<PostTagDto> tags) {
        this.postId = postId;
        this.bookImage = bookImage;
        this.onelineReview = onelineReview;
        this.userName = userName;
        this.bookTitle = bookTitle;
        this.likeCount = likeCount;
        this.commentsCount = commentsCount;
        this.tags = tags;
    }
}
