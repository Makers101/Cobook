package com.ssafy.cobook.controller;


import com.ssafy.cobook.service.ProfileService;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor

public class ProfileController {
    private final ProfileService profileService;

//    @ApiOperation(value = "자신의 회원정보를 수정한다")
//    @PutMapping("/{userId}}")
//    public ResponseEntity<UserResponseIdDto> updateProfile(@PathVariable Long userId, @RequestBody final UserUpdateDto userUpdateDto) {
//        return ResponseEntity.status(HttpStatus.OK).body(profileService.updateUserInfo(userId, userUpdateDto));
//    }

    @ApiOperation(value = "특정 회원의 정보를 가져온다")
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> getUserInfo(@PathVariable("userId") final Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getUserInfo(userId));
    }

//    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
//    @PostMapping("/{postId}/comments")
//    public ResponseEntity<Void> addComments(@ApiIgnore final Authentication authentication,
//                                            @PathVariable("postId") final Long postId,
//                                            @RequestBody final CommentsReqDto dto) {

//        @ApiOperation(value = "팔로우하기")
//    @GetMapping("/follow")
//    public ResponseEntity<Integer> follwUses(@RequestBody ProfileFollowUserDto profileFollowUserDto){
//
//    }
}
