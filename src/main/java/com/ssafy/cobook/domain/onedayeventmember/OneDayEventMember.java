package com.ssafy.cobook.domain.onedayeventmember;

import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.domain.user.User;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "one_day_event_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "one_day_event_id")
    private OneDayEvent oneDayEvent;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    public OneDayEventMember(User user, OneDayEvent oneDayEvent, MemberRole role) {
        this.user = user;
        this.oneDayEvent = oneDayEvent;
        this.role = role;
    }

    public boolean isNotLeader() {
        return !this.role.equals(MemberRole.LEADER);
    }

    public void signOutEvent() {
        this.user.removeEvents(this);
    }
}
