package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.PostService;
import com.ssafy.cobook.service.dto.post.PostDetailResDto;
import com.ssafy.cobook.service.dto.post.PostResponseDto;
import com.ssafy.cobook.service.dto.post.PostSaveReqDto;
import com.ssafy.cobook.service.dto.post.PostSaveResDto;
import com.ssafy.cobook.service.dto.postbookmark.PostBookMarkReqDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsReqDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsResDto;
import com.ssafy.cobook.service.dto.postlike.PostLikeReqDto;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(("/api/posts"))
@RequiredArgsConstructor
public class PostController {

    private static final Logger logger = LoggerFactory.getLogger(PostController.class);

    private final PostService postService;

    @ApiOperation(value = "게시글 전체 조회")
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getAllPosts() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPosts());
    }

    @ApiOperation(value = "사용자가 게시글을 작성", response = PostSaveResDto.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<PostSaveResDto> savePosts(@ApiIgnore final Authentication authentication,
                                                    @RequestBody final PostSaveReqDto requestDto) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePosts(requestDto, userId));
    }

//    @ApiOperation(value = "팔로우 한 사람들의 게시글을 조회한다.", response = PostResDto.class)
//    @GetMapping("/users/{userId}/follows")
//    public ResponseEntity<List<PostResDto>> getPosts(@PathVariable("userId") final Long userId) {
//        return ResponseEntity.status(HttpStatus.OK).body(postService.getFollowPosts(userId));
//    }

    @ApiOperation(value = "게시글의 상세 내용을 조회한다.", response = PostDetailResDto.class)
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResDto> details(@PathVariable("postId") final Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.details(postId));
    }

    @ApiOperation(value = "게시글의 좋아요를 누른다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/likes")
    public ResponseEntity<Void> likePosts(@ApiIgnore final Authentication authentication,
                                          @RequestBody final PostLikeReqDto reqDto) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        postService.likePosts(reqDto, userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글을 북마크한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/bookmarks")
    public ResponseEntity<Void> bookMarks(@ApiIgnore final Authentication authentication,
                                          @RequestBody final PostBookMarkReqDto reqDto) {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        postService.bookMarks(reqDto, userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글의 댓글 내역을 조회한다", response = CommentsResDto.class)
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentsResDto>> getComments(@PathVariable("postId") final Long postId) {
        List<CommentsResDto> comments = postService.getComments(postId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @ApiOperation(value = "게시글에 댓글을 단다")
    @PostMapping("/comments")
    public ResponseEntity<Void> addComments(@RequestBody final CommentsReqDto dto) {
        postService.addComments(dto);
        return ResponseEntity.ok().build();
    }
}
