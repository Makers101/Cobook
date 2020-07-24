package com.ssafy.cobook.service.dto.reading;

import com.ssafy.cobook.domain.reading.Reading;
import com.ssafy.cobook.service.dto.book.BookSimpleResDto;
import com.ssafy.cobook.service.dto.post.PostSimpleResDto;
import com.ssafy.cobook.service.dto.question.QuestionResDto;
import com.ssafy.cobook.service.dto.user.UserSimpleResDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReadingDetailResDto {

    private String title;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private String oneLineDescription;
    private Integer participantCount;
    private List<UserSimpleResDto> users;
    private BookSimpleResDto book;
    private List<QuestionResDto> questions;
    private List<PostSimpleResDto> memberPosts;

    public ReadingDetailResDto(Reading reading, List<UserSimpleResDto> users, List<QuestionResDto> questions, List<PostSimpleResDto> posts) {
        this.title = reading.getTitle();
        this.dateTime = reading.getDateTime();
        this.place = reading.getPlace();
        this.description = reading.getDescription();
        this.oneLineDescription = reading.getOneLineDescription();
        this.participantCount = users.size();
        this.users = users;
        this.book = new BookSimpleResDto(reading.getBook());
        this.questions = questions;
        this.memberPosts = posts;
    }
}
