package com.ssafy.cobook.service.dto.clubevent;

import com.ssafy.cobook.domain.clubevent.ClubEvent;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import com.ssafy.cobook.service.dto.post.PostByMembersResDto;
import com.ssafy.cobook.service.dto.question.QuestionResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventDetailResDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime datetime;
    private String place;
    private Integer participantCnt;
    private Boolean closed;
    private Boolean isMember;
    private BookSimpleResDto book;
    private List<QuestionResDto> questions;
    private ClubEventMemberResponseDto leader;
    private List<ClubEventMemberResponseDto> participants;
    private List<PostByMembersResDto> memberPosts;

    public ClubEventDetailResDto(ClubEvent clubEvent, List<PostByMembersResDto> memberPosts, boolean isMember) {
        this.id = clubEvent.getId();
        this.name = clubEvent.getTitle();
        this.datetime = clubEvent.getDateTime();
        this.place = clubEvent.getPlace();
        this.description = clubEvent.getDescription();
        this.closed = clubEvent.getClosed();
        this.book = new BookSimpleResDto(clubEvent.getBook());
        this.questions = clubEvent.getQuestions().stream()
                .map(QuestionResDto::new)
                .collect(Collectors.toList());
        this.leader = clubEvent.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.LEADER))
                .findFirst()
                .map(ClubEventMemberResponseDto::new)
                .get();
        this.participants = clubEvent.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.MEMBER))
                .map(ClubEventMemberResponseDto::new)
                .collect(Collectors.toList());
        this.memberPosts = memberPosts;
        this.isMember = isMember;
        this.participantCnt = participants.size() + 1;
    }
}
