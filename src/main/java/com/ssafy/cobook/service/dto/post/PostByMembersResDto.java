package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.service.dto.book.BookDetailsDto;
import com.ssafy.cobook.service.dto.club.ClubByPostDto;
import com.ssafy.cobook.service.dto.user.UserByPostDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostByMembersResDto {

    private Long id;
    private String onelineReview;
    private Integer rank;
    private Boolean open;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> likeUsers;
    private List<Long> bookmarkUsers;

    public PostByMembersResDto(Post post) {
        this.id = post.getId();
        this.onelineReview = post.getOnelineReview();
        this.rank = post.getRank();
        this.open = post.getOpen();
        this.createdAt = post.getCreatDateTime();
        this.updatedAt = post.getLastModifiedDate();
        this.likeUsers = post.getPostLikes().stream()
                .map(p -> p.getUser().getId())
                .collect(Collectors.toList());
        this.bookmarkUsers = post.getBookMarks().stream()
                .map(b -> b.getUser().getId())
                .collect(Collectors.toList());
    }
}
