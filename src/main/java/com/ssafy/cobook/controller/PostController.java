package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.PostService;
import com.ssafy.cobook.service.dto.post.PostDetailResDto;
import com.ssafy.cobook.service.dto.post.PostResDto;
import com.ssafy.cobook.service.dto.post.PostSaveReqDto;
import com.ssafy.cobook.service.dto.post.PostSaveResDto;
import com.ssafy.cobook.service.dto.postbookmark.PostBookMarkReqDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsResDto;
import com.ssafy.cobook.service.dto.postlike.PostLikeReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(("/api/posts"))
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostSaveResDto> savePosts(@RequestBody final PostSaveReqDto reqDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePosts(reqDto));
    }

    @GetMapping("/users/{userId}/follows")
    public ResponseEntity<List<PostResDto>> getPosts(@PathVariable("userId") final Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getFollowPosts(userId));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResDto> details(@PathVariable("postId") final Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.details(postId));
    }

    @PostMapping("/likes")
    public ResponseEntity<Void> likePosts(@RequestBody final PostLikeReqDto reqDto) {
        postService.likePosts(reqDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/bookmarks")
    public ResponseEntity<Void> bookMarks(@RequestBody final PostBookMarkReqDto reqDto) {
        postService.bookMarks(reqDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentsResDto>> getComments(@PathVariable("postId") final Long postId) {
        List<CommentsResDto> comments = postService.getComments(postId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }
}
