package com.ssafy.cobook.controller;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.service.UserService;
import com.ssafy.cobook.service.dto.UserUpdateDto;
import com.ssafy.cobook.service.dto.book.BookResponseDto;
import com.ssafy.cobook.service.dto.user.*;
import com.ssafy.cobook.util.JwtTokenProvider;
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
import javax.xml.ws.Response;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor

public class UserController {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @ApiOperation(value = "모든 회원을 조회한다.")
    @GetMapping("/")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
    }

    @ApiOperation(value = "이메일, 패스워드, 유저명을 받아서 회원가입한다.", response = UserResponseIdDto.class)
    @PostMapping("/signup")
    public ResponseEntity<UserResponseIdDto> saveUser(@RequestBody final UserSaveRequestDto userSaveRequestDto) {
        UserResponseIdDto userResponseDto = userService.saveUser(userSaveRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }


    @ApiOperation(value = "이메일, 패스워드를 받아서 로그인하여 토큰을 반환한다")
    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody final UserLoginRequestDto userLoginRequestDto) {
        User user = userService.login(userLoginRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtTokenProvider.createToken(user.getId(), user.getRoles()));
    }

    @ApiOperation(value = "회원정보를 수정한다")
    @PostMapping("/modify")
    public ResponseEntity<UserResponseIdDto> signin(@RequestBody final UserUpdateDto userUpdateDto) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserInfo(userUpdateDto));
    }

    @ApiOperation(value = "이메일을 받아서 인증코드를 보낸다.", response = String.class)
    @PostMapping("/checkEmail")
    public ResponseEntity<UserResponseIdDto> sendEmail(@RequestBody final String email, HttpServletRequest httpServletRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.preparedAndSend(email, httpServletRequest));
    }

    @ApiOperation(value = "인증코드를 확인하여 올바른지 확인한다", response = String.class)
    @PostMapping("/checkAuth")
    public ResponseEntity<String> checkAuth(@RequestBody final UserResetPwdRequestDto userResetPwdRequestDto, HttpSession httpSession) {
        String result = userService.checkEmailAuth(userResetPwdRequestDto.getRandomCode(), httpSession);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "인증코드가 올바르다면 비밀번호를 새로 입력받아 저장한다.", response = String.class)
    @PostMapping("/resetPassword")
    public ResponseEntity<UserResponseIdDto> resetPassword(@RequestBody final UserUpdatePwdDto userUpdatePwdDto) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updatePassword(userUpdatePwdDto));
    }
}
