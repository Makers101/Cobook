package com.ssafy.cobook.domain.meetup;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.meetupmember.MeetUpMember;
import com.ssafy.cobook.domain.meetupquestion.MeetUpQuestion;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetUp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meetup_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    @OneToMany(mappedBy = "meetUp", cascade = CascadeType.ALL)
    private List<MeetUpQuestion> questions = new ArrayList<>();

    @OneToMany(mappedBy = "meetUp", cascade = CascadeType.ALL)
    private List<MeetUpMember> members = new ArrayList<>();

    private String title;
    private LocalDateTime dateTime;
    private String place;
    private String description;
    private String onelineDescription;
    private Integer participantCount;
}
