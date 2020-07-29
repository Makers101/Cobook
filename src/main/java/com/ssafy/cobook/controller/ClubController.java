package com.ssafy.cobook.controller;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.service.ClubService;
import com.ssafy.cobook.service.PostService;
import com.ssafy.cobook.service.ReadingService;
import com.ssafy.cobook.service.dto.club.*;
import com.ssafy.cobook.service.dto.post.*;
import com.ssafy.cobook.service.dto.reading.ReadingDetailResDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveReqDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveResDto;
import com.ssafy.cobook.util.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubController {

    private static final Logger logger = LoggerFactory.getLogger(ClubController.class);

    private final ClubService clubService;
    private final PostService postService;
    private final ReadingService readingService;
    private final FileUtil fileService;

    @ApiOperation(value = "클럽을 생성한다", response = ClubCreateResDto.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<ClubCreateResDto> createClub(@ApiIgnore final Authentication authentication,
                                                       @RequestBody ClubCreateReqDto reqDto) throws IOException {
        Long userId = ((User) authentication.getPrincipal()).getId();
        ClubCreateResDto resDto = clubService.create(userId, reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @ApiOperation(value = "클럽 이미지를 저장한다.")
    @PostMapping("/{clubId}/images")
    public ResponseEntity<Void> savefiles(@PathVariable("clubId") final Long clubId, @RequestParam MultipartFile clubImg) throws IOException {
        clubService.fileSave(clubId, clubImg);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "클럽 이미지를 가져온다")
    @GetMapping("/images/{clubId}")
    public ResponseEntity<Resource> getImages(@PathVariable("clubId") final Long id, HttpServletRequest request) {
        String path = clubService.getFilePath(id);
        Resource resource = fileService.loadFileAsResource(path);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            logger.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "클럽에 가입한다.")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{clubId}/apply")
    public ResponseEntity<Void> enrollClub(@ApiIgnore final Authentication authentication,
                                           @PathVariable("clubId") final Long clubId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        clubService.joinClub(userId, clubId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "클럽 가입 신청을 승인한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{clubId}/apply/{clubMemberId}/approve")
    public ResponseEntity<Void> processApply(@ApiIgnore final Authentication authentication,
                                             @PathVariable("clubId") final Long clubId,
                                             @PathVariable("clubMemberId") final Long clubMemberId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        clubService.approve(clubId, clubMemberId, userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "클럽 가입 신청을 거절한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{clubId}/apply/{clubMemberId}/reject")
    public ResponseEntity<Void> processReject(@ApiIgnore final Authentication authentication,
                                              @PathVariable("clubId") final Long clubId,
                                              @PathVariable("clubMemberId") final Long clubMemberId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        clubService.reject(clubId, clubMemberId, userId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "클럽 전체 조회", response = ClubResDto.class)
    @GetMapping
    public ResponseEntity<List<ClubResDto>> getClubs() {
        List<ClubResDto> resDto = clubService.getClubList();
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
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

    @ApiOperation(value = "리딩을 생성한다.", response = ReadingSaveResDto.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{clubId}/readings")
    public ResponseEntity<ReadingSaveResDto> makeReading(@ApiIgnore final Authentication authentication,
                                                         @PathVariable("clubId") final Long clubId,
                                                         @RequestBody final ReadingSaveReqDto reqDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        ReadingSaveResDto resDto = readingService.makeReading(userId, clubId, reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
    }

    @ApiOperation(value = "리딩의 상세정보를 조회한다", response = ReadingDetailResDto.class)
    @GetMapping("/{clubId}/readings/{readingId}")
    public ResponseEntity<ReadingDetailResDto> getReadingDetail(@PathVariable("clubId") final Long clubId,
                                                                @PathVariable("readingId") final Long readingId) {
        ReadingDetailResDto resDto = readingService.getDetails(clubId, readingId);
        return ResponseEntity.status(HttpStatus.OK).body(resDto);
    }

    @ApiOperation(value = "리딩에 가입한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/{clubId}/readings/{readingId}/apply")
    public ResponseEntity<Void> applyReading(@ApiIgnore final Authentication authentication,
                                             @PathVariable("clubId") final Long clubId,
                                             @PathVariable("readingId") final Long readingId) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        readingService.applyReading(readingId, clubId, userId);
        return ResponseEntity.ok().build();
    }

}
