package com.ssafy.cobook.service.dto.post;

import com.ssafy.cobook.domain.post.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.ssafy.cobook.config.WebMvcConfig.SERVER_PORT;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostByMembersResDto {

    private Long id;
    private Long userId;
    private String nickName;
    private String profileImg;
    private String onelineReview;
    private Integer rank;
    private Boolean open;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<Long> likeUsers;
    private List<Long> bookmarkUsers;

    public PostByMembersResDto(Post post) {
        this.id = post.getId();
        this.userId = post.getUser().getId();
        this.nickName = post.getUser().getNickName();
        if (post.getUser().getProfileImg() != null) {
            this.profileImg = "https://i3a111.p.ssafy.io:"+ SERVER_PORT + "/api/profile/images/" + this.userId;
        }
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

    public static List<PostByMembersResDto> getDto(List<Post> posts) {
        List<PostByMembersResDto> ret = new ArrayList<>();
        for (Post post : posts) {
            ret.add(new PostByMembersResDto(post));
        }
        return ret;
    }
}
