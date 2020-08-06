package com.ssafy.cobook.service.dto.onedayevent;

import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OneDayEventResponseDto implements Comparable<OneDayEventResponseDto> {

    private Long id;
    private String name;
    private LocalDateTime datetime;
    private String place;
    private String description;
    private Integer participantCnt;
    private Integer capacity;
    private Boolean closed;
    private BookSimpleResDto book;

    public OneDayEventResponseDto(OneDayEvent oneDayEvent) {
        this.id = oneDayEvent.getId();
        this.name = oneDayEvent.getTitle();
        this.datetime = oneDayEvent.getDateTime();
        this.place = oneDayEvent.getPlace();
        this.description = oneDayEvent.getDescription();
        this.participantCnt = oneDayEvent.getMembers().size();
        this.capacity = oneDayEvent.getCapacity();
        this.closed = oneDayEvent.getClosed();
        this.book = new BookSimpleResDto(oneDayEvent.getBook());
    }

    @Override
    public int compareTo(OneDayEventResponseDto o) {
        return -this.datetime.compareTo(o.datetime);
    }
}
