package com.ssafy.cobook.controller;

import com.ssafy.cobook.service.UserService;
import com.ssafy.cobook.service.dto.TokenResponse;
import com.ssafy.cobook.service.dto.user.*;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

//http:/.localhost:8080/swagger-ui.html
public class UserController {
    private final UserService userService; // 유저 서비스에 로직
    private static final Logger logger = LoggerFactory.getLogger(UserController.class); // 로그찍기

    @ApiOperation(value = "이메일, 패스워드, 유저명을 받아서 회원가입한다.", response = UserResponseDto.class)
    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto>saveUser(@RequestBody final UserSaveRequestDto userSaveRequestDto){
        UserResponseDto userResponseDto = userService.saveUser(userSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }


    @ApiOperation(value = "이메일, 패스워드를 받아서 로그인하여 토큰을 반환한다", response = TokenResponse.class)
    @PostMapping("/signin")
    public TokenResponse login(@RequestBody UserLoginRequestDto userLoginRequestDto){
        String token = userService.createToken(userLoginRequestDto);
        Long userId = userService.login(userLoginRequestDto);
        return new TokenResponse(token, userId);
    }

    @ApiOperation(value = "이메일을 받아서 인증코드를 보낸다.", response = String.class)
    @PostMapping("/checkEmail")
    public ResponseEntity<UserResponseDto> sendEmail(@RequestBody final String email, HttpServletRequest httpServletRequest){
        return ResponseEntity.status(HttpStatus.OK).body(userService.preparedAndSend(email, httpServletRequest));
    }

    @ApiOperation(value = "인증코드를 확인하여 올바른지 확인한다", response = String.class)
    @PostMapping("/checkAuth")
    public ResponseEntity<String> checkAuth(@RequestBody final UserResetPwdRequestDto userResetPwdRequestDto, HttpSession httpSession){
        String result = userService.checkEmailAuth(userResetPwdRequestDto.getRandomCode(), httpSession);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "인증코드가 올바르다면 비밀번호를 새로 입력받아 저장한다.", response = String.class)
    @PostMapping("/resetPassword")
    public ResponseEntity<UserResponseDto> resetPassword(@RequestBody final UserUpdatePwdDto userUpdatePwdDto){
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updatePassword(userUpdatePwdDto));
    }
}
