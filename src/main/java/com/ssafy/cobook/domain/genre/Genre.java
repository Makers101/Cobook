package com.ssafy.cobook.domain.genre;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genre_id")
    private Long id;

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<ClubGenre> clubList = new ArrayList<>();

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<UserGenre> userList = new ArrayList<>();

    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Book> bookList = new ArrayList<>();

    private String genre;
}
