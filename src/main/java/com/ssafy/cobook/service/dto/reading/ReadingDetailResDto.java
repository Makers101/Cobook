package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.reading.Reading;
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
public class ReadingDetailResDto {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String place;
    private Integer participantCnt;
    private Boolean closed;
    private Boolean isMember;
    private BookSimpleResDto book;
    private List<QuestionResDto> questions;
    private ReadingMemberResponseDto leader;
    private List<ReadingMemberResponseDto> participants;
    private List<PostByMembersResDto> memberPosts;

    public ReadingDetailResDto(Reading reading, List<PostByMembersResDto> memberPosts, boolean isMember) {
        this.id = reading.getId();
        this.name = reading.getTitle();
        this.dateTime = reading.getDateTime();
        this.place = reading.getPlace();
        this.description = reading.getDescription();
        this.closed = reading.getClosed();
        this.book = new BookSimpleResDto(reading.getBook());
        this.questions = reading.getQuestions().stream()
                .map(QuestionResDto::new)
                .collect(Collectors.toList());
        this.leader = reading.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.LEADER))
                .findFirst()
                .map(ReadingMemberResponseDto::new)
                .get();
        this.participants = reading.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.MEMBER))
                .map(ReadingMemberResponseDto::new)
                .collect(Collectors.toList());
        this.memberPosts = memberPosts;
        this.isMember = isMember;
        this.participantCnt = participants.size() + 1;
    }
}
