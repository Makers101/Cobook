package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.user.UserLoginRequestDto;
import com.ssafy.cobook.service.dto.user.UserResponseDto;
import com.ssafy.cobook.service.dto.user.UserSaveRequestDto;
import com.ssafy.cobook.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserResponseDto saveUser(UserSaveRequestDto userSaveRequestDto){
        if(userRepository.findByEmail(userSaveRequestDto.getEmail()).isPresent()){
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
        User user = userSaveRequestDto.toEntity();
        user = userRepository.save(user);
        return new UserResponseDto(user.getId());
    }

    @Transactional
    public String createToken(UserLoginRequestDto userLoginRequestDto){
        String email = userLoginRequestDto.getEmail();
        User user = getUser(email);
        if(!user.checkPassword(userLoginRequestDto.getPassword())){
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }
        return jwtTokenProvider.createToken(userLoginRequestDto.getEmail());
    }

    private User getUser(final String email){
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new UserException(String.format("%s는(은) 가입되지 않은 이메일입니다.",email), ErrorCode.UNSIGNED));
    }

    public Long login(UserLoginRequestDto userLoginRequestDto){
        User user = getUser(userLoginRequestDto.getEmail());
        if(!user.getPassword().equals(userLoginRequestDto.getPassword())){
            throw new UserException(ErrorCode.WRONG_PASSWORD);
        }
        return user.getId();
    }
}
