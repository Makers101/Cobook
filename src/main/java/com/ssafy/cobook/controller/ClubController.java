package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.ClubService;
import com.ssafy.cobook.service.PostService;
import com.ssafy.cobook.service.dto.club.*;
import com.ssafy.cobook.service.dto.post.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

    private final ClubService clubService;
    private final PostService postService;

    @ApiOperation(value = "클럽을 생성한다", response = ClubCreateResDto.class)
    @PostMapping
    public ResponseEntity<ClubCreateResDto> createClub(@ApiIgnore final Authentication authentication,
                                                       @RequestParam MultipartFile clubImg,
                                                       @RequestBody final ClubCreateReqDto reqDto) throws IOException {
        Long userId = Long.parseLong((String) authentication.getPrincipal());
        ClubCreateResDto resDto = clubService.create(userId, reqDto, clubImg);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @ApiOperation(value = "클럽에 가입한다.")
    @PostMapping("/enroll")
    public ResponseEntity<Void> enrollClub(@RequestBody final ClubEnrollReqDto reqDto) {
        clubService.joinClub(reqDto);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "클럽 전체 조회", response = ClubResDto.class)
    @GetMapping
    public ResponseEntity<List<ClubResDto>> getClubs() {
        List<ClubResDto> resDto = clubService.getClubList();
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @ApiOperation(value = "모집중인 클럽 조회", response = ClubResDto.class)
    @GetMapping("/recruit")
    public ResponseEntity<List<ClubResDto>> getRecruitClub() {
        List<ClubResDto> resDtos = clubService.getClubList().stream()
                .filter(ClubResDto::getRecruit)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(resDtos);
    }

    @ApiOperation(value = "클럽 상세 조회", response = ClubDetailResDto.class)
    @GetMapping("/{clubId}")
    public ResponseEntity<ClubDetailResDto> getClubDetails(@PathVariable("clubId") final Long clubId) {
        ClubDetailResDto dto = clubService.getClubDetail(clubId);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @ApiOperation(value = "클럽 게시글 조회", response = PostByClubResponseDto.class)
    @GetMapping("/{clubId}/posts")
    public ResponseEntity<List<PostByClubResponseDto>> getClubPosts(@PathVariable("clubId") final Long clubId) {
        List<PostByClubResponseDto> responseDto = postService.getClubPosts(clubId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @ApiOperation(value = "클럽 게시글 작성", response = PostSaveResDto.class)
    @PostMapping("/{clubId}/posts")
    public ResponseEntity<PostSaveResDto> saveClubPosts(@PathVariable("clubId") final Long clubId,
                                                        @RequestBody final PostSaveByClubReqDto requsetDto) {
        PostSaveResDto resDto = postService.saveClubPosts(requsetDto, clubId);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }
}
