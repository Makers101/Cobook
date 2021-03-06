package com.ssafy.cobook.service.dto.onedayevent;

import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
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
public class OneDayEventDetailsDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime datetime;
    private String place;
    private Integer participantCnt;
    private Boolean closed;
    private Boolean isMember;
    private String roomUrl;
    private BookSimpleResDto book;
    private Integer capacity;
    private List<QuestionResDto> questions;
    private OneDayEventMemberResponseDto leader;
    private List<OneDayEventMemberResponseDto> participants;
    private List<PostByMembersResDto> memberPosts;

    public OneDayEventDetailsDto(OneDayEvent oneDayEvent, List<PostByMembersResDto> memberPosts, boolean isMember) {
        this.id = oneDayEvent.getId();
        this.name = oneDayEvent.getTitle();
        this.datetime = oneDayEvent.getDateTime();
        this.place = oneDayEvent.getPlace();
        this.description = oneDayEvent.getDescription();
        this.closed = oneDayEvent.getClosed();
        this.roomUrl = oneDayEvent.getRoomUrl();
        this.book = new BookSimpleResDto(oneDayEvent.getBook());
        this.questions = oneDayEvent.getQuestions().stream()
                .map(QuestionResDto::new)
                .collect(Collectors.toList());
        this.leader = oneDayEvent.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.LEADER))
                .findFirst()
                .map(OneDayEventMemberResponseDto::new)
                .get();
        this.participants = oneDayEvent.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.MEMBER))
                .map(OneDayEventMemberResponseDto::new)
                .collect(Collectors.toList());
        this.memberPosts = memberPosts;
        this.isMember = isMember;
        this.participantCnt = participants.size() + 1;
        this.capacity = oneDayEvent.getCapacity();
    }

}
