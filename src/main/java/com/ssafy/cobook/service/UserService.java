package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.club.ClubResDto;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JavaMailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final ClubMemberRepository clubMemberRepository;
    private final ProfileService profileService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseIdDto saveUser(UserSaveRequestDto userSaveRequestDto) {
        if (userRepository.findByEmail(userSaveRequestDto.getEmail()).isPresent()) {
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
        String encodePassword = passwordEncoder.encode(userSaveRequestDto.getPassword());

        User user = userSaveRequestDto.toEntity();
        user.changePassword(encodePassword);
        user = userRepository.save(user);
        return new UserResponseIdDto(user.getId());
    }


    private User getUser(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format("%s는(은) 가입되지 않은 이메일입니다.", email), ErrorCode.UNSIGNED));
    }

    public User login(UserLoginRequestDto userLoginRequestDto) {

        User user = getUser(userLoginRequestDto.getEmail());

        String encodePassword = user.getPassword();
        String rawPassword = userLoginRequestDto.getPassword();

        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }

        return user;
    }

    public ProfileResponseDto getMyInfo(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("존재 하지 않는 회원 입니다.",ErrorCode.UNEXPECTED_USER));

        List<ClubResDto> clubList = clubMemberRepository.findAllByUser(user).stream()
                .map(ClubMember::getClub)
                .map(ClubResDto::new)
                .sorted()
                .collect(Collectors.toList());

        List<UserByFollowDto> followerList = profileService.getFollowerList(user.getId(), user.getId());
        List<UserByFollowDto> followingList = profileService.getFollowingList(user.getId(), user.getId());

        ProfileResponseDto profileResponseDto = new ProfileResponseDto(user, clubList, followerList, followingList);
        return profileResponseDto;
    }

    @Transactional
    public UserResponseIdDto preparedAndSend(String recipient, HttpServletRequest httpServletRequest) {
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
        return new UserResponseIdDto(user.getId());
    }

    @Transactional
    public String checkEmailAuth(int inputAuthCode, HttpSession httpSession) {
        String originalCode = (String) httpSession.getAttribute("authCode");
        String randomCode = String.valueOf(inputAuthCode);

        if (!originalCode.equals(randomCode)) {
            throw new UserException(ErrorCode.WRONG_EMAIL_CHECK_AUTH_CODE);
        }
        return "Check Ok";
    }

    @Transactional
    public UserResponseIdDto updatePassword(UserUpdatePwdDto userUpdatePwdDto) {
        User user = userRepository.findByEmail(userUpdatePwdDto.getEmail())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));
        String encodePassword = passwordEncoder.encode(userUpdatePwdDto.getPassword());
        user.changePassword(encodePassword);
        return new UserResponseIdDto(user.getId());
    }

}
