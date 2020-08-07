package com.ssafy.cobook.domain.clubeventmember;

import com.ssafy.cobook.domain.clubevent.ClubEvent;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_event_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_event_id")
    private ClubEvent clubEvent;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public ClubEventMember(User user, ClubEvent clubEvent, MemberRole role) {
        this.user = user;
        this.clubEvent = clubEvent;
        this.role = role;
    }

    public void removeUser() {
        this.user.removeReading(this);
    }

    public boolean isNotLeader() {
        return !this.role.equals(MemberRole.LEADER);
    }
}
