package com.ssafy.cobook.service.dto.clubevent;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.clubevent.ClubEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ClubEventByClubResDto {
    private Long clubId;
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private String oneLineDescription;
    private Boolean closed;
    private Book book;

    public ClubEventByClubResDto(ClubEvent clubEvent) {
        this.clubId = clubEvent.getClub().getId();
        this.id = clubEvent.getId();
        this.name = clubEvent.getTitle();
        this.dateTime = clubEvent.getDateTime();
        this.place = clubEvent.getPlace();
        this.description = clubEvent.getDescription();
        this.closed = clubEvent.getClosed();
        this.book = clubEvent.getBook();
    }
}
