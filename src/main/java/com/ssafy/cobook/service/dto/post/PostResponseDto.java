package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.service.dto.book.BookByPostDto;
import com.ssafy.cobook.service.dto.club.ClubByPostDto;
import com.ssafy.cobook.service.dto.tag.TagByPostDto;
import com.ssafy.cobook.service.dto.user.UserByPostDto;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostResponseDto {

    @ApiModelProperty(position = 1)
    private Long id;
    @ApiModelProperty(position = 2)
    private UserByPostDto user;
    @ApiModelProperty(position = 2)
    private ClubByPostDto club;
    @ApiModelProperty(position = 3)
    private BookByPostDto book;
    @ApiModelProperty(position = 4)
    private String onelineReview;
    @ApiModelProperty(position = 5)
    private Integer rank;
    @ApiModelProperty(position = 6)
    private Boolean open;
    @ApiModelProperty(position = 7)
    private String review;
    @ApiModelProperty(position = 8)
    private LocalDateTime createdAt;
    @ApiModelProperty(position = 9)
    private LocalDateTime updatedAt;
    @ApiModelProperty(position = 10)
    private Boolean isClub;
    @ApiModelProperty(position = 11)
    private List<Long> likeUsers;
    @ApiModelProperty(position = 12)
    private List<Long> bookmarkUsers;
    @ApiModelProperty(position = 13)
    private List<TagByPostDto> tags;

    public PostResponseDto(Post post) {
        this.id = post.getId();
        if (post.getIsClub()) {
            this.club = new ClubByPostDto(post.getClub());
        } else {
            this.user = new UserByPostDto(post.getUser());
        }
        this.book = new BookByPostDto(post.getBook());
        this.onelineReview = post.getOnelineReview();
        this.rank = post.getRank();
        this.open = post.getOpen();
        this.review = post.getReview();
        this.createdAt = post.getCreatDateTime();
        this.updatedAt = post.getLastModifiedDate();
        this.isClub = post.getIsClub();
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
    }
}
