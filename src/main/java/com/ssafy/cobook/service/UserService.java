package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.user.UserResponseDto;
import com.ssafy.cobook.service.dto.user.UserSaveRequestDto;
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

    @Transactional
    public UserResponseDto saveUser(UserSaveRequestDto userSaveRequestDto){
        if(userRepository.findByEmail(userSaveRequestDto.getEmail()).isPresent()){
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
        User user = userSaveRequestDto.toEntity();
        user = userRepository.save(user);
        return new UserResponseDto(user.getId());
    }
}
