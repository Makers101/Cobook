package com.ssafy.cobook.domain.clubmember;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cmember_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public ClubMember(User user, Club club, MemberRole role) {
        this.user = user;
        this.club = club;
        this.role = role;
    }

    public void enroll(Club club) {
        this.club = club;
    }

    public boolean isNotLeader() {
        return !this.role.equals(MemberRole.LEADER);
    }

    public boolean onWait() {
        return this.role.equals(MemberRole.WAITING);
    }

    public void changeRole(MemberRole role) {
        this.role = role;
    }
}
