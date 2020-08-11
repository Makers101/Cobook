package com.ssafy.cobook.service.dto.onedayevent;

import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import com.ssafy.cobook.service.dto.question.QuestionResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class OneDayEventBySearchDto {
    private Long id;
    private Integer capacity;
    private Integer participantCnt;
    private String name;
    private String description;
    private String place;
    private Boolean closed;
    private BookSimpleResDto book;
    private LocalDateTime datetime;

    public OneDayEventBySearchDto(OneDayEvent oneDayEvent){
        this.id = oneDayEvent.getId();
        this.name = oneDayEvent.getTitle();
        this.datetime = oneDayEvent.getDateTime();
        this.place = oneDayEvent.getPlace();
        this.description = oneDayEvent.getDescription();
        this.closed = oneDayEvent.getClosed();
        this.book = new BookSimpleResDto(oneDayEvent.getBook());
        this.participantCnt = oneDayEvent.getMembers().stream()
                .filter(m -> m.getRole().equals(MemberRole.MEMBER))
                .map(OneDayEventMemberResponseDto::new)
                .collect(Collectors.toList()).size() + 1;
        this.capacity = oneDayEvent.getCapacity();
    }

}
