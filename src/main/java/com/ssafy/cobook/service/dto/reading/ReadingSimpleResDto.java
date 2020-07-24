package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.reading.Reading;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingSimpleResDto {

    private Long readingId;
    private String onelineDescription;
    private String place;
    private LocalDateTime datetime;
    private Integer participant_num;
    private Long bookId;
    private String bookImage;
    private String bookTitle;

    public ReadingSimpleResDto(Reading reading) {
        this.readingId = reading.getId();
        this.onelineDescription = reading.getDescription();
        this.place = reading.getPlace();
        this.datetime = reading.getDateTime();
        this.participant_num = reading.getMembers().size();
        this.bookId = reading.getBook().getId();
        this.bookImage = reading.getBook().getBookImg();
        this.bookTitle = reading.getBook().getTitle();
    }
}
