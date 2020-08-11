package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.onedayevent.OneDayEventRepository;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.service.dto.book.BookBySearchResDto;
import com.ssafy.cobook.service.dto.post.PostBySearchResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SearchService {
    private final PostRepository postRepository;
    private final BookRepository bookRepository;

    public List<BookBySearchResDto> searchBooks(String keyword) {
        return bookRepository.findByKeyword(keyword);
    }
    public List<PostBySearchResDto> searchPosts(String keyword) {
        return postRepository.findByKeyword(keyword);
    }

}
