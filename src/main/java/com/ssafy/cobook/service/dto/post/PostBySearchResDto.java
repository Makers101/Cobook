package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.service.dto.book.BookByPostDto;
import com.ssafy.cobook.service.dto.book.BookDetailsDto;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import com.ssafy.cobook.service.dto.club.ClubByPostDto;
import com.ssafy.cobook.service.dto.tag.TagByPostDto;
import com.ssafy.cobook.service.dto.user.UserByPostDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class PostBySearchResDto {

    private Long id;
    private BookByPostDto book;
    private ClubByPostDto club;
    private UserByPostDto user;
    private List<Long> bookmarkUsers;
    private List<Long> likeUsers;
    private Integer commentsCount;
    private Integer rank;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isClub;
    private Boolean open;
    private String onelineReview;
    private String review;
    private List<TagByPostDto> tags;

    public PostBySearchResDto(Post post){
        this.id = post.getId();
        this.book = new BookByPostDto(post.getBook());
        if (post.ofClub()) {
            this.club = new ClubByPostDto(post.getClub());
        } else {
            this.user = new UserByPostDto(post.getUser());
        }
        this.bookmarkUsers = post.getBookMarks().stream()
                .map(b->b.getUser().getId())
                .collect(Collectors.toList());
        this.likeUsers = post.getPostLikes().stream()
                .map(p->p.getUser().getId())
                .collect(Collectors.toList());
        this.commentsCount = post.getComments().size();
        this.rank = post.getRank();
        this.createdAt = post.getCreatDateTime();
        this.updatedAt = post.getLastModifiedDate();
        this.isClub = post.getIsClub();
        this.open = post.getOpen();
        this.onelineReview = post.getOnelineReview();
        this.review = post.getReview();
        this.tags = post.getTags().stream()
                .map(PostTag::getTag)
                .map(TagByPostDto::new)
                .collect(Collectors.toList());
    }
}
