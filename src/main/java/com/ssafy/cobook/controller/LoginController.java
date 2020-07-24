package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.UserService;
import com.ssafy.cobook.service.dto.TokenResponse;
import com.ssafy.cobook.service.dto.user.UserLoginRequestDto;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;

    @ApiOperation(value = "이메일, 패스워드를 받아서 로그인하여 토큰을 반환한다", response = TokenResponse.class)
    @PostMapping
    public TokenResponse login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        String token = userService.createToken(userLoginRequestDto);
        Long userId = userService.login(userLoginRequestDto);
        return new TokenResponse(token, userId);
    }
}
