package com.ssafy.cobook.controller;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.service.SearchService;
import com.ssafy.cobook.service.dto.book.BookBySearchResDto;
import com.ssafy.cobook.service.dto.club.ClubBySearchResDto;
import com.ssafy.cobook.service.dto.onedayevent.OneDayEventBySearchDto;
import com.ssafy.cobook.service.dto.post.PostBySearchResDto;
import com.ssafy.cobook.service.dto.user.UserBySearchResDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @ApiOperation(value = "유저 검색하기 (유저 이름으로 검색)")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/users/{keyword}")
    public ResponseEntity<List<UserBySearchResDto>> searchUsers(@ApiIgnore final Authentication authentication, @PathVariable("keyword") final String keyword) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchUsers(keyword, userId));
    }

    @ApiOperation(value = "책 검색하기 (책 이름과 저자명으로 검색)")
    @GetMapping("/books/{keyword}")
    public ResponseEntity<List<BookBySearchResDto>> searchBooks(@PathVariable("keyword") final String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchBooks(keyword));
    }


    @ApiOperation(value = "피드 검색하기 (한줄 리뷰, 리뷰에 포함된 내용으로 검색)")
    @GetMapping("/posts/{keyword}")
    public ResponseEntity<List<PostBySearchResDto>> searchPosts(@PathVariable("keyword") final String keyword) {
        // 검색한 keyword가 들어간 oneline review
        // review
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchPosts(keyword));
    }

    @ApiOperation(value = "클럽 검색하기 (클럽 이름으로 검색)")
    @GetMapping("/clubs/{keyword}")
    public ResponseEntity<List<ClubBySearchResDto>> searchClubs(@PathVariable("keyword") final String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchClubs(keyword));
    }

    @ApiOperation(value = "원데이이벤트 검색하기 (oneDay의 책 이름과 oneDay의 이벤트 이름으로 검색)")
    @GetMapping("/onedayevents/{keyword}")
    public ResponseEntity<List<OneDayEventBySearchDto>> searchOneDayEvents(@PathVariable("keyword") final String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.searchOneDayEvents(keyword));
    }
}
