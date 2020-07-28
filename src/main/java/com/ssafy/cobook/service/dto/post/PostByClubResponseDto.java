package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.service.dto.book.BookByPostDto;
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
public class PostByClubResponseDto {

    private Long id;
    private ClubByPostDto club;
    private BookByPostDto book;
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

    public PostByClubResponseDto(Post post) {
        this.id = post.getId();
        this.club = new ClubByPostDto(post.getClub());
        this.book = new BookByPostDto(post.getBook());
        this.onelineReview = post.getOnelineReview();
        this.rank = post.getRank();
        this.open = post.getOpen();
        this.review = post.getReview();
        this.createdAt = post.getCreatDateTime();
        this.updatedAt = post.getLastModifiedDate();
        this.isClub = post.getIsClub();
        this.likeUsers = post.getPostLikes().stream()
                .map(p -> p.getUser().getId())
                .collect(Collectors.toList());
        this.bookmarkUsers = post.getBookMarks().stream()
                .map(b -> b.getUser().getId())
                .collect(Collectors.toList());
        this.tags = post.getTags().stream()
                .map(PostTag::getTag)
                .map(TagByPostDto::new)
                .collect(Collectors.toList());
    }
}
