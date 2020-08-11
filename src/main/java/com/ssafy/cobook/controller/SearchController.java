package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.SearchService;
import com.ssafy.cobook.service.dto.book.BookBySearchResDto;
import com.ssafy.cobook.service.dto.club.ClubBySearchResDto;
import com.ssafy.cobook.service.dto.post.PostBySearchResDto;
import com.ssafy.cobook.service.dto.user.UserBySearchResDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @ApiOperation(value = "유저 검색하기 (유저 이름으로 검색)")
    @GetMapping("/user/{keyword}")
    public ResponseEntity<List<UserBySearchResDto>> searchUsers(@PathVariable("keyword") final String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchUsers(keyword));
    }

    @ApiOperation(value = "책 검색하기 (책 이름과 저자명으로 검색)")
    @GetMapping("/book/{keyword}")
    public ResponseEntity<List<BookBySearchResDto>> searchBooks(@PathVariable("keyword") final String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchBooks(keyword));
    }

    @ApiOperation(value = "피드 검색하기 (한줄 리뷰, 리뷰에 포함된 내용으로 검색)")
    @GetMapping("/post/{keyword}")
    public ResponseEntity<List<PostBySearchResDto>> searchPosts(@PathVariable("keyword") final String keyword) {
        // 검색한 keyword가 들어간 oneline review
        // review
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchPosts(keyword));
    }

    @ApiOperation(value = "클럽 검색하기 (클럽 이름으로 검색)")
    @GetMapping("/club/{keyword}")
    public ResponseEntity<List<ClubBySearchResDto>> searchClubs(@PathVariable("keyword") final String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchClubs(keyword));
    }


}
