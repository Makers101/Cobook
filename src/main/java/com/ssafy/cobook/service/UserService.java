package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.user.UserLoginRequestDto;
import com.ssafy.cobook.service.dto.user.UserResponseDto;
import com.ssafy.cobook.service.dto.user.UserSaveRequestDto;
import com.ssafy.cobook.service.dto.user.UserUpdatePwdDto;
import com.ssafy.cobook.util.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final JavaMailSender emailSender;
    private BCryptPasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public UserResponseDto saveUser(UserSaveRequestDto userSaveRequestDto) {
        if (userRepository.findByEmail(userSaveRequestDto.getEmail()).isPresent()) {
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
        passwordEncoder = new BCryptPasswordEncoder(10);
        logger.info(userSaveRequestDto.getPassword());
        String encodePassword = passwordEncoder.encode(userSaveRequestDto.getPassword());

        User user = userSaveRequestDto.toEntity();
        user.changePassword(encodePassword);
        user = userRepository.save(user);
        return new UserResponseDto(user.getId());
    }

    @Transactional
    public String createToken(UserLoginRequestDto userLoginRequestDto) {
        String email = userLoginRequestDto.getEmail();
        User user = getUser(email);
        passwordEncoder = new BCryptPasswordEncoder(10);

        String pwd = user.getPassword();
        String rawPwd = userLoginRequestDto.getPassword();

        logger.info(pwd);
        logger.info(rawPwd);

        if (!passwordEncoder.matches(rawPwd,pwd)) {
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }
        return jwtTokenProvider.createToken(userLoginRequestDto.getEmail());
    }

    private User getUser(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format("%s는(은) 가입되지 않은 이메일입니다.", email), ErrorCode.UNSIGNED));
    }

    public Long login(UserLoginRequestDto userLoginRequestDto) {

        User user = getUser(userLoginRequestDto.getEmail());

        if (!user.getPassword().equals(userLoginRequestDto.getPassword())) {
            throw new UserException(ErrorCode.WRONG_PASSWORD);
        }
        return user.getId();
    }

    @Transactional
    public UserResponseDto preparedAndSend(String recipient, HttpServletRequest httpServletRequest) {
        logger.info(recipient+ "da!!");
        User user = getUser(recipient);

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@cobook.co.kr");
            messageHelper.setTo(user.getEmail());
            messageHelper.setSubject("cobook 비밀번호 재설정 코드");

            HttpSession session = httpServletRequest.getSession(true);
            int randomCode = new Random().nextInt(900000) + 100000;

            String authCode = String.valueOf(randomCode);
            session.setAttribute("authCode", authCode);

            StringBuilder stringBuilder = new StringBuilder();
            String content = stringBuilder.append("cobook의 인증코드는 ")
                    .append(randomCode).append("입니다.")
                    .append("30분 안에 입력해주세요.")
                    .toString();
            messageHelper.setText(content, true);
        };
        emailSender.send(messagePreparator);
        return new UserResponseDto(user.getId());
    }

    @Transactional
    public String checkEmailAuth(int inputAuthCode, HttpSession httpSession){
        String originalCode = (String)httpSession.getAttribute("authCode");
        String randomCode = String.valueOf(inputAuthCode);

        if(!originalCode.equals(randomCode)){
            throw new UserException(ErrorCode.WRONG_EMAIL_CHECK_AUTH_CODE);
        }
        return "Check Ok";
    }

    @Transactional
    public UserResponseDto updatePassword(UserUpdatePwdDto userUpdatePwdDto){
        passwordEncoder = new BCryptPasswordEncoder(10);

        User user = userRepository.findByEmail(userUpdatePwdDto.getEmail())
                .orElseThrow(()-> new UserException(ErrorCode.UNSIGNED));
        String encodePassword = passwordEncoder.encode(userUpdatePwdDto.getPassword());
        user.changePassword(encodePassword);
        return new UserResponseDto(user.getId());
    }
}
