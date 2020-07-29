package com.ssafy.cobook.controller;


import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.service.ProfileService;
import com.ssafy.cobook.service.dto.profile.ProfileFollowUserDto;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.user.UserByFollowDto;
import com.ssafy.cobook.service.dto.user.UserResponseIdDto;
import com.ssafy.cobook.service.dto.user.UserUpdateReqDto;
import com.ssafy.cobook.util.FileUtil;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final FileUtil fileService;

    @ApiOperation(value = "자신의 회원정보를 수정한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PutMapping()
    public ResponseEntity<UserResponseIdDto> updateProfile(@ApiIgnore final Authentication authentication,
                                                           @RequestBody final UserUpdateReqDto userUpdateDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(profileService.updateUserInfo(userId, userUpdateDto));
    }

    @ApiOperation(value = "사용자의 이미지를 저장한다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("/images")
    public ResponseEntity<Void> updateUserPicture(@ApiIgnore final Authentication authentication,
                                                  @RequestParam final MultipartFile profileImg) throws IOException {
        Long userId = ((User) authentication.getPrincipal()).getId();
        profileService.saveImg(userId, profileImg);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "사용자의 이미지를 가져온다")
    @GetMapping("/images/{userId}")
    public ResponseEntity<Resource> getProfileImg(@PathVariable("userId") final Long userId, HttpServletRequest request) {
        String path = profileService.getFilePath(userId);
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

    @ApiOperation(value = "특정 회원의 정보를 가져온다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> getUserInfo(@ApiIgnore Authentication authentication, @PathVariable("userId") Long toUserId) {
        Long fromUserId =  ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getUserInfo(fromUserId, toUserId));
    }

    @ApiOperation(value = "팔로우 버튼을 누른다")
    @PostMapping("/follow")
    public ResponseEntity<Void> followUser(@RequestBody ProfileFollowUserDto profileFollowUserDto){
        profileService.addFollow(profileFollowUserDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "팔로잉한 사람들의 리스트 가져오기")
    @PostMapping("/following")
    public ResponseEntity<List<UserByFollowDto>>getFollowingList(@RequestBody ProfileFollowUserDto profileFollowUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getFollowingList(profileFollowUserDto));
    }


    @ApiOperation(value = "팔로우한 사람들의 리스트가져오기")
    @PostMapping("/follower")
    public ResponseEntity<List<UserByFollowDto>>getFollowerList(@RequestBody ProfileFollowUserDto profileFollowUserDto){
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getFollowerList(profileFollowUserDto));
    }
}
