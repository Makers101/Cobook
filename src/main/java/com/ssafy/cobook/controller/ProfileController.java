package com.ssafy.cobook.controller;


import com.ssafy.cobook.service.ProfileService;
import com.ssafy.cobook.service.dto.profile.ProfileFollowUserDto;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.user.UserByFollowDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor

public class ProfileController {
    private final ProfileService profileService;

    @ApiOperation(value = "특정 회원의 정보를 가져온다")
    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponseDto> getUserInfo(@PathVariable("userId") final Long userId) {
        return ResponseEntity.status(HttpStatus.OK).body(profileService.getUserInfo(userId));
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
