package com.ssafy.cobook.domain.follow;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "follow_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromUserId")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "toUserId")
    private User toUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    private Boolean isClub;

    @Builder
    public Follow(User fromUser, User toUser, boolean isClub){
        this.fromUser = fromUser;
        this.toUser = toUser;
        this.isClub = isClub;
    }

    @Builder
    public Follow(User fromUser, Club club, boolean isClub){
        this.fromUser = fromUser;
        this.club = club;
        this.isClub = isClub;
    }

}
