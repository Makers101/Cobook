package com.ssafy.cobook.service.dto.clubevent;

import com.ssafy.cobook.domain.clubevent.ClubEvent;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ClubEventSimpleResDto implements Comparable<ClubEventSimpleResDto> {

    private Long clubId;
    private Long eventId;
    private String name;
    private LocalDateTime datetime;
    private String place;
    private Integer participantCnt;
    private Boolean closed;
    private BookSimpleResDto book;

    public ClubEventSimpleResDto(ClubEvent clubEvent) {
        this.clubId = clubEvent.getClub().getId();
        this.eventId = clubEvent.getId();
        this.name = clubEvent.getTitle();
        this.place = clubEvent.getPlace();
        this.datetime = clubEvent.getDateTime();
        this.participantCnt = clubEvent.getMembers().size();
        this.closed = clubEvent.getClosed();
        this.book = new BookSimpleResDto(clubEvent.getBook());
    }

    @Override
    public int compareTo(ClubEventSimpleResDto o) {
        return -this.datetime.compareTo(o.datetime);
    }
}
