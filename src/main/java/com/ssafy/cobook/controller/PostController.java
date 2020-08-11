package com.ssafy.cobook.controller;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.service.PostService;
import com.ssafy.cobook.service.dto.post.*;
import com.ssafy.cobook.service.dto.postcomment.CommentsReqDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsResDto;
import com.ssafy.cobook.service.dto.tag.TagResponseDto;
import com.ssafy.cobook.util.PageRequest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@CrossOrigin(origins = "*")
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
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(postService.savePosts(requestDto, userId));
    }

    @ApiOperation(value = "게시글의 상세 내용을 조회한다.", response = PostDetailResDto.class)
    @GetMapping("/{postId}")
    public ResponseEntity<PostDetailResDto> details(@PathVariable("postId") final Long postId) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.details(postId));
    }

    @ApiOperation(value = "게시글의 좋아요를 누른다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{postId}/likes")
    public ResponseEntity<Void> likePosts(@ApiIgnore final Authentication authentication,
                                          @PathVariable("postId") final Long postId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.likePosts(postId, userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글을 북마크한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{postId}/bookmarks")
    public ResponseEntity<Void> bookMarks(@ApiIgnore final Authentication authentication,
                                          @PathVariable("postId") final Long postId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.bookMarks(postId, userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글의 댓글 내역을 조회한다", response = CommentsResDto.class)
    @GetMapping("/{postId}/comments")
    public ResponseEntity<List<CommentsResDto>> getComments(@PathVariable("postId") final Long postId) {
        List<CommentsResDto> comments = postService.getComments(postId);
        return ResponseEntity.status(HttpStatus.OK).body(comments);
    }

    @ApiOperation(value = "게시글에 댓글을 단다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{postId}/comments")
    public ResponseEntity<Void> addComments(@ApiIgnore final Authentication authentication,
                                            @PathVariable("postId") final Long postId,
                                            @RequestBody final CommentsReqDto dto) {
        System.out.println(authentication.toString());
        System.out.println(authentication.getPrincipal().toString());
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.addComments(userId, postId, dto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글의 태그 전체를 반환한다")
    @GetMapping("/tags")
    public ResponseEntity<List<TagResponseDto>> getAllTags() {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllTags());
    }

    @ApiOperation(value = "사용자가 게시글을 수정", response = PostSaveResDto.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping("/{postId}")
    public ResponseEntity<Void> updatePost(@ApiIgnore final Authentication authentication,
                                           @PathVariable("postId") final Long postId,
                                           @RequestBody final PostUpdateReqDto requestDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.updatePost(postId, userId, requestDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "댓글을 삭제한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{postId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComments(@ApiIgnore final Authentication authentication,
                                               @PathVariable("postId") final Long postId,
                                               @PathVariable("commentId") final Long commentId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.deleteComment(userId, postId, commentId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글을 삭제한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePosts(@ApiIgnore final Authentication authentication,
                                            @PathVariable("postId") final Long postId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        postService.deletePosts(userId, postId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "게시글을 페이징 처리해 전체 조회한다.", response = PostResponseDto.class)
    @GetMapping("/page")
    public ResponseEntity<Page<PostResponseDto>> getAllPostsByPaging(final PageRequest pageRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(postService.getAllPostsPage(pageRequest));
    }

    @ApiOperation(value = "팔로우한 사람의 게시글을 조회한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/follows")
    public ResponseEntity<List<PostResponseDto>> getFollowerPosts(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<PostResponseDto> responseDtos = postService.getFollowerPosts(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDtos);
    }

    @ApiOperation(value = "내 관심 장르 게시글 조회")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/genres")
    public ResponseEntity<List<PostResponseDtoByGenre>> getGenresPosts(@ApiIgnore final Authentication authentication) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        List<PostResponseDtoByGenre> response = postService.getGenresPosts(userId);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
