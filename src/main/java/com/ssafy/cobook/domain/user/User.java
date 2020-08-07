package com.ssafy.cobook.domain.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.cobook.domain.booklike.BookLike;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMember;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.postbookmark.PostBookMark;
import com.ssafy.cobook.domain.postcomment.PostComment;
import com.ssafy.cobook.domain.postlike.PostLike;
import com.ssafy.cobook.domain.clubeventmember.ClubEventMember;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String password;
    private String nickName;
    private String description;
    private String profileImg;
    private Boolean accept;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> roles = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private PlatformType platformType;

    private String platformId;

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
    private List<ClubEventMember> readingMembers = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<UserGenre> userGenres = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BookLike> bookLikes = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<OneDayEventMember> oneDayEventMembers = new ArrayList<>();

    @Builder
    public User(String email, String password, String nickName, Boolean accept, PlatformType platformType, List<String> roles) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.accept = accept;
        this.platformType = platformType;
        this.roles = roles;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeAccept(boolean accept){
        this.accept = accept;
    }

    public void enrollClub(ClubMember clubMember) {
        this.clubMembers.add(clubMember);
    }

    public void addComments(PostComment postComment) {
        this.comments.add(postComment);
    }

    public void addPosts(Post post) {
        this.posts.add(post);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getUsername() {
        return this.id.toString();
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void enrollReading(ClubEventMember clubEventMember) {
        if(this.readingMembers.contains(clubEventMember)) {
            readingMembers.remove(clubEventMember);
            return;
        }
        this.readingMembers.add(clubEventMember);
    }

    public void deleteLike(PostLike postLike) {
        this.postLikes.remove(postLike);
    }

    public void addLikes(PostLike postLike) {
        this.postLikes.add(postLike);
    }

    public void deleteBookMark(PostBookMark postBookMark) {
        this.bookMarks.remove(postBookMark);
    }

    public void addBookMarks(PostBookMark postBookMark) {
        this.bookMarks.add(postBookMark);
    }

    public void removeGenre(UserGenre userGenre) {
        this.userGenres.remove(userGenre);
    }

    public void addGenres(UserGenre userGenre) {
        this.userGenres.add(userGenre);
    }

    public void updateInfo(String nicnName, String description) {
        this.nickName = nicnName;
        this.description = description;
    }

    public void setProfile(String profileImg) {
        this.profileImg = profileImg;
    }

    public void removeComment(PostComment postComment) {
        this.comments.remove(postComment);
    }

    public void removePost(Post post) {
        this.posts.remove(post);
    }

    public void removeBookMark(PostBookMark postBookMark) {
        this.bookMarks.remove(postBookMark);
    }

    public void removePostLike(PostLike postLike) {
        this.postLikes.remove(postLike);
    }

    public void removeReading(ClubEventMember delete) {
        this.readingMembers.remove(delete);
    }

    public void removeClub(ClubMember clubMember) {
        this.clubMembers.remove(clubMember);
    }

    public void enrollOneDayEvent(OneDayEventMember member) {
        this.oneDayEventMembers.add(member);
    }

    public void removeEvents(OneDayEventMember member) {
        this.oneDayEventMembers.remove(member);
    }
}
