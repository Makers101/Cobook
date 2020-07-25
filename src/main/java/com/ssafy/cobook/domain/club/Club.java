package com.ssafy.cobook.domain.club;

import com.ssafy.cobook.domain.baseEntity.BaseEntity;
import com.ssafy.cobook.domain.baseEntity.BaseTimeEntity;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.follow.Follow;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.reading.Reading;
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
    private List<Reading> readingList = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<Follow> followList = new ArrayList<>();

    private String name;
    private String onelineDescription;
    private String description;
    private String residence;
    private String clubImg;
    private Integer participantCount;
    private Boolean recruit;

    @Builder
    public Club(String name, String onelineDescription, String description, String residence) {
        this.name = name;
        this.onelineDescription = onelineDescription;
        this.description = description;
        this.residence = residence;
        participantCount = 0;
        recruit = false;
    }

    public void enrolls(ClubMember member) {
        members.add(member);
        member.enroll(this);
        this.participantCount += 1;
    }

    public void setGenres(List<ClubGenre> genres) {
        this.genres = genres;
    }

    public void setProfile(String url) {
        this.clubImg = url;
    }

    public void enrollReading(Reading reading) {
        this.readingList.add(reading);
    }
}