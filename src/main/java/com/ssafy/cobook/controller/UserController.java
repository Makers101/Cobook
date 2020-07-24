package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.UserService;
import com.ssafy.cobook.service.dto.user.UserResponseDto;
import com.ssafy.cobook.service.dto.user.UserSaveRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.spring.web.json.Json;

@Slf4j
@RestController
@RequestMapping("/api/users") // 프론트에서 restAPI 보낼 때 기본 맵핑 주소
@RequiredArgsConstructor

//http:/.localhost:8080/swagger-ui.html
public class UserController {
    private final UserService userService; // 유저 서비스에 로직
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); // 로그찍기

    @ApiOperation(value = "이메일, 패스워드, 유저명을 받아서 회원가입한다.", response = Json.class)
    @PostMapping
    public ResponseEntity<UserResponseDto>saveUser(@RequestBody final UserSaveRequestDto userSaveRequestDto){
        UserResponseDto userResponseDto = userService.saveUser(userSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }
}
