package com.ssafy.cobook.controller;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.UserService;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.user.*;
import com.ssafy.cobook.util.JwtTokenProvider;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.annotations.ApiIgnore;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public ResponseEntity<UserResponseIdDto> signUp(@RequestBody final UserSaveRequestDto userSaveRequestDto) {
        UserResponseIdDto userResponseDto = userService.signUp(userSaveRequestDto, false);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
    }

    @ApiOperation(value = "이메일 인증을 진행하여 회원가입을 한다")
    @GetMapping("/authentication/{token}")
    public RedirectView authenticateEmail(@PathVariable("token") String token) {
        userService.checkEmailToken(token);

        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://i3a111.p.ssafy.io/signup/success");
        return redirectView;
    }

    @ApiOperation(value = "이메일, 패스워드를 받아서 로그인하여 토큰을 반환한다")
    @PostMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody final UserLoginRequestDto userLoginRequestDto) {
        User user = userService.login(userLoginRequestDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(jwtTokenProvider.createToken(user.getId(), user.getRoles()));
    }


    @ApiOperation(value = "자기 자신의 정보를 가져온다")
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping
    public ResponseEntity<ProfileResponseDto> getMyInfo(@ApiIgnore final Authentication authentication) {
        if( authentication == null) {
            return ResponseEntity.ok().build();
        }
        Long userId = ((User) authentication.getPrincipal()).getId();
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.getMyInfo(userId));
    }

    @ApiOperation(value = "비밀번호 찾기")
    @PostMapping("/password")
    public ResponseEntity<Void> getPassword(@RequestBody UserEmailSimpleDto userEmailSimpleDto) {
        userService.getPassword(userEmailSimpleDto, true);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "비밀번호 인증메일을 통해 비밀번호 변경 페이지로 보내준다")
    @GetMapping("/resetPassword/{token}")
    public RedirectView goResetPassword(Model m, @PathVariable("token") String token) {
        RedirectView redirectView = new RedirectView();
        redirectView.setUrl("http://i3a111.p.ssafy.io/password/change");
        Map<String, String> attributes = new HashMap();
        attributes.put("jwt", token);
//        redirectView.setUrl("http://localhost:8081/password/find");
        redirectView.setAttributesMap(attributes);
        return redirectView;

    }

    @ApiOperation(value = "인증코드가 올바르다면 비밀번호를 새로 입력받아 저장한다.", response = String.class)
    @ApiImplicitParams({@ApiImplicitParam(name = "jwt", value = "JWT Token", required = true, dataType = "string", paramType = "header")})
    @PostMapping("resetPassword/update")
    public ResponseEntity<Void> resetPassword(@ApiIgnore final Authentication authentication, @RequestBody final UserUpdatePwdDto userUpdatePwdDto) {
        Long userId = ((User) authentication.getPrincipal()).getId();
        userService.updatePassword(userId, userUpdatePwdDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
