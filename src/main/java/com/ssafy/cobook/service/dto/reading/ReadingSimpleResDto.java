package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.reading.Reading;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingSimpleResDto {

    private Long id;
    private String name;
    private LocalDateTime datetime;
    private String place;
    private Integer participantCnt;
    private Boolean closed;
    private BookSimpleResDto book;

    public ReadingSimpleResDto(Reading reading) {
        this.id = reading.getId();
        this.name = reading.getTitle();
        this.place = reading.getPlace();
        this.datetime = reading.getDateTime();
        this.participantCnt = reading.getMembers().size();
        this.closed = reading.getClosed();
        this.book = new BookSimpleResDto(reading.getBook());
    }
}
