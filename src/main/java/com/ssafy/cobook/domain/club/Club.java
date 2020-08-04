package com.ssafy.cobook.domain.club;

import com.ssafy.cobook.domain.baseEntity.BaseTimeEntity;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.follow.Follow;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.clubevent.ClubEvent;
import com.ssafy.cobook.service.dto.club.ClubUpdateRequestDto;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Club extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long id;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubMember> members = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubGenre> genres = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<ClubEvent> clubEvents = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Follow> followList = new ArrayList<>();

    private String name;
    private String onelineDescription;
    private String description;
    private String residence;
    private String clubImg;
    private Boolean recruit;

    @Builder
    public Club(String name, String onelineDescription, String description, String residence) {
        this.name = name;
        this.onelineDescription = onelineDescription;
        this.description = description;
        this.residence = residence;
        recruit = false;
    }

    public void enrolls(ClubMember member) {
        members.add(member);
        member.enroll(this);
    }

    public void setGenres(List<ClubGenre> genres) {
        this.genres = genres;
    }

    public void setProfile(String url) {
        this.clubImg = url;
    }

    public void enrollReading(ClubEvent clubEvent) {
        this.clubEvents.add(clubEvent);
    }

    public void addPosts(Post post) {
        this.posts.add(post);
    }

    public void changeRecruit() {
        this.recruit = !this.recruit;
    }

    public void removeMember(ClubMember clubMember) {
        this.members.remove(clubMember);
    }

    public void updateInfos(ClubUpdateRequestDto requestDto) {
        this.name = requestDto.getName();
        this.description = requestDto.getDescription();
        this.onelineDescription = requestDto.getOnelineDescription();
        this.residence = requestDto.getResidence();
    }

    public void removeGenre(ClubGenre genre) {
        this.genres.remove(genre);
    }

    public void addGenres(List<ClubGenre> clubGenres) {
        this.genres.addAll(clubGenres);
    }

    public void removeEvents(ClubEvent event) {
        this.clubEvents.remove(event);
    }
}