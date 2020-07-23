package com.ssafy.cobook.domain.user;

import com.ssafy.cobook.domain.booklike.BookLike;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.meetupmember.MeetUpMember;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.postbookmark.PostBookMark;
import com.ssafy.cobook.domain.postcomment.PostComment;
import com.ssafy.cobook.domain.postlike.PostLike;
import com.ssafy.cobook.domain.readingmember.ReadingMember;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String userName;
    private String description;
    private String profileImg;

    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostLike> postLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostBookMark> bookMarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostComment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ClubMember> clubMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<ReadingMember> readingMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGenre> userGenres = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookLike> bookLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<MeetUpMember> meetUpMembers = new ArrayList<>();

    @Builder
    public User(String email, String password, String userName, PlatformType platformType){
        this.email = email;
        this.password = password;
        this.userName = userName;
        this.platformType = platformType;
    }
}
