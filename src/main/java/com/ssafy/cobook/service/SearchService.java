package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.follow.FollowRepository;
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
import com.ssafy.cobook.service.dto.user.UserBySearchResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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


    public List<UserBySearchResDto> searchUsers(String keyword, Long userId) {
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));
        List<User> userList = userRepository.findByKeyword(keyword);
        List<UserBySearchResDto> searchUserList = new ArrayList<>();

        Boolean isFollow;

        for (User user: userList){
            if(followRepository.findByToUser(fromUser, user).isPresent()){
                isFollow = true;
            } else{
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
        return postRepository.findByKeyword(keyword);
    }

    public List<ClubBySearchResDto> searchClubs(String keyword) {
        return clubRepository.findByKeyword(keyword);
    }

    public List<OneDayEventBySearchDto> searchOneDayEvents(String keyword) {
        return oneDayEventRepository.findByKeyword(keyword);
    }
}
