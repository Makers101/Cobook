package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.service.dto.book.BookDetailsDto;
import com.ssafy.cobook.service.dto.club.ClubByPostDto;
import com.ssafy.cobook.service.dto.tag.TagByPostDto;
import com.ssafy.cobook.service.dto.user.UserByPostDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostDetailResDto {

    private Long id;
    private UserByPostDto user;
    private ClubByPostDto club;
    private BookDetailsDto book;
    private String onelineReview;
    private Integer rank;
    private Boolean open;
    private String review;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isClub;
    private List<Long> likeUsers;
    private List<Long> bookmarkUsers;
    private List<TagByPostDto> tags;
    private List<PostSimpleResDto> posts;

    public PostDetailResDto(Post post, List<PostSimpleResDto> posts) {
        this.id = post.getId();
        if (post.ofClub()) {
            this.club = new ClubByPostDto(post.getClub());
        } else {
            this.user = new UserByPostDto(post.getUser());
        }
        this.onelineReview = post.getOnelineReview();
        this.review = post.getReview();
        this.rank = post.getRank();
        this.open = post.getOpen();
        this.isClub = post.getIsClub();
        this.book = new BookDetailsDto(post.getBook());
        this.createdAt = post.getCreatDateTime();
        this.updatedAt = post.getLastModifiedDate();
        this.likeUsers = post.getPostLikes().stream()
                .map(p->p.getUser().getId())
                .collect(Collectors.toList());
        this.bookmarkUsers = post.getBookMarks().stream()
                .map(b->b.getUser().getId())
                .collect(Collectors.toList());
        this.tags = post.getTags().stream()
                .map(PostTag::getTag)
                .map(TagByPostDto::new)
                .collect(Collectors.toList());
        this.posts = posts;
    }
}
