package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubgenre.ClubGenreRepository;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.domain.onedayevent.OneDayEventRepository;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.book.BookBySearchResDto;
import com.ssafy.cobook.service.dto.club.ClubBySearchResDto;
import com.ssafy.cobook.service.dto.onedayevent.OneDayEventBySearchDto;
import com.ssafy.cobook.service.dto.post.PostBySearchResDto;
import com.ssafy.cobook.service.dto.tag.TagByPostDto;
import com.ssafy.cobook.service.dto.user.UserBySearchResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PostRepository postRepository;
    private final ClubRepository clubRepository;
    private final OneDayEventRepository oneDayEventRepository;
    private final FollowRepository followRepository;
    private final GenreRepository genreRepository;
    private final ClubGenreRepository clubGenreRepository;

    public List<UserBySearchResDto> searchUsers(String keyword, Long userId) {
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));
        List<User> userList = userRepository.findByKeyword(keyword);
        List<UserBySearchResDto> searchUserList = new ArrayList<>();

        Boolean isFollow;

        for (User user : userList) {
            if (followRepository.findByToUserAndFromUser(fromUser.getId(), user.getId()).isPresent()) {
                isFollow = true;
            } else {
                isFollow = false;
            }
            searchUserList.add(new UserBySearchResDto(user, isFollow));
        }

        return searchUserList;
    }

    public List<BookBySearchResDto> searchBooks(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }

    public List<PostBySearchResDto> searchPosts(String keyword) {
        return new ArrayList<>(postRepository.findByKeyword(keyword));
    }

    public List<ClubBySearchResDto> searchClubs(String keyword) {
        HashSet<Club> res = new HashSet<>(clubRepository.findByKeyword(keyword));
        if (!genreRepository.findAllByGenreName(keyword).isEmpty()) {
            List<Genre> genre = genreRepository.findAllByGenreName(keyword);
            for (Genre g : genre) {
                HashSet<Club> temp = clubGenreRepository.findAllByGenre(g).stream()
                        .map(ClubGenre::getClub)
                        .collect(Collectors.toCollection(HashSet::new));
                res.addAll(temp);
            }
        }
        return res.stream()
                .map(ClubBySearchResDto::new)
                .collect(Collectors.toList());
    }

    public List<OneDayEventBySearchDto> searchOneDayEvents(String keyword) {
        HashSet<OneDayEvent> res = new HashSet<>(oneDayEventRepository.findByKeyword(keyword));
        if (!genreRepository.findAllByGenreName(keyword).isEmpty()) {
            List<Genre> genre = genreRepository.findAllByGenreName(keyword);
            for (Genre g : genre) {
                HashSet<OneDayEvent> temp = new HashSet<>(oneDayEventRepository.findAllByGenre(g));
                res.addAll(temp);
            }
        }
        return res.stream()
                .map(OneDayEventBySearchDto::new)
                .collect(Collectors.toList());
    }
}
