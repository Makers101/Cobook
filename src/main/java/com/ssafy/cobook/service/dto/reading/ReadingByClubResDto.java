package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.reading.Reading;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import com.ssafy.cobook.service.dto.reading.ReadingSimpleResDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ReadingByClubResDto {
    private Long clubId;
    private Long id;
    private String name;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private String oneLineDescription;
    private Boolean closed;


    public ReadingByClubResDto(Reading reading) {
        this.clubId = reading.getClub().getId();
        this.id = reading.getId();
        this.name = reading.getTitle();
        this.dateTime = reading.getDateTime();
        this.place = reading.getPlace();
        this.description = reading.getDescription();
        this.oneLineDescription = reading.getOneLineDescription();
        this.closed = reading.getClosed();
    }
}
