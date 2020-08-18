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
import com.ssafy.cobook.util.JwtTokenProvider;
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

import java.net.URL;
import java.util.List;
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
    private final JwtTokenProvider jwtTokenProvider;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Transactional
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public UserResponseIdDto signUp(UserSaveRequestDto userSaveRequestDto, Boolean isFind) {
        if (userRepository.findByNickName(userSaveRequestDto.getNickName()).isPresent()) {
            throw new UserException("이미 존재하는 닉네임입니다", ErrorCode.MEMBER_DUPLICATED_NICKNAME);
        }

        if (userRepository.findByEmail(userSaveRequestDto.getEmail()).isPresent()) {
            throw new UserException("이미 가입된 메일입니다", ErrorCode.MEMBER_DUPLICATED_EMAIL);
        }
        String encodePassword = passwordEncoder.encode(userSaveRequestDto.getPassword());
        User user = userSaveRequestDto.toEntity();
        user.changePassword(encodePassword);
        user = userRepository.save(user);

        String token = jwtTokenProvider.createToken(user.getId(), user.getRoles());

        // 이메일 인증 메일을 보낸다
        preparedAndSend(userSaveRequestDto.getEmail(), isFind, token);

        return new UserResponseIdDto(user.getId());
    }

    @Transactional
    public void checkEmailToken(String token) {
        Long id = Long.valueOf(jwtTokenProvider.getId(token));
        userRepository.findById(id).orElseThrow(() -> new UserException(ErrorCode.WRONG_EMAIL_CHECK_AUTH));
        userRepository.updateAccept(id, true);
    }

    private User getUser(final String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(String.format("%s는(은) 가입되지 않은 이메일입니다.", email), ErrorCode.UNEXPECTED_USER));
    }

    public User login(UserLoginRequestDto userLoginRequestDto) {

        User user = getUser(userLoginRequestDto.getEmail());


        String encodePassword = user.getPassword();
        String rawPassword = userLoginRequestDto.getPassword();

        if (user.getAccept() == null) {
            throw new UserException("아직 이메일 인증이 되지 않은 회원입니다.", ErrorCode.WRONG_EMAIL_CHECK_AUTH);
        }
        if (!passwordEncoder.matches(rawPassword, encodePassword)) {
            throw new UserException("잘못된 비밀번호입니다.", ErrorCode.WRONG_PASSWORD);
        }

        return user;
    }

    public ProfileResponseDto getMyInfo(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("존재 하지 않는 회원 입니다.", ErrorCode.UNEXPECTED_USER));

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

    private void preparedAndSend(String recipient, boolean isFind, String token) {
        StringBuilder stringBuilder = new StringBuilder();

        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("noreply@cobook.co.kr");
            messageHelper.setTo(recipient);
            if (!isFind) { // 이메일 인증
                messageHelper.setSubject("Cobook 회원가입 인증이메일입니다.");
                URL url = new URL("https://i3a111.p.ssafy.io:8090/api/users/authentication/" + token);
                String content = stringBuilder.append("하단의 링크로 접속하여 인증해주세요!")
                        .append("\n")
                        .append(url)
                        .toString();
                messageHelper.setText(content, true);
            } else {
                messageHelper.setSubject("Cobook 비밀번호 변경 메일입니다.");
                URL url = new URL("https://i3a111.p.ssafy.io:8090/api/users/resetPassword/" + token);
//                URL url = new URL("http://localhost:8080/api/users/resetPassword/" + token);
                String content = stringBuilder.append("하단의 링크로 접속하여 새로운 비밀번호를 입력해주세요!")
                        .append("\n")
                        .append(url)
                        .toString();
                messageHelper.setText(content, true);
            }
        };
        emailSender.send(messagePreparator);
    }


    public void resend(ResendEmailDto resendEmailDto) {
        User user = getUser(resendEmailDto.getEmail());
        String token = jwtTokenProvider.createToken(user.getId(), user.getRoles());

        if (resendEmailDto.getType().equals("signup")) {
            preparedAndSend(user.getEmail(), false, token);
        } else {
            preparedAndSend(user.getEmail(), true, token);
        }
    }

    public void getPassword(UserEmailSimpleDto userEmailSimpleDto, boolean isFind) {
        String userEmail = userEmailSimpleDto.getEmail();
        User user = userRepository.findByEmail(userEmailSimpleDto.getEmail())
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        String token = jwtTokenProvider.createToken(user.getId(), user.getRoles());
        preparedAndSend(userEmail, isFind, token);
    }


    @Transactional
    public void updatePassword(String token, UserUpdatePwdDto userUpdatePwdDto) {
        Long userId = Long.valueOf(jwtTokenProvider.getId(token));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        String encodePassword = passwordEncoder.encode(userUpdatePwdDto.getPassword());
        userRepository.updatePassword(user.getId(), encodePassword);
    }

    public String socialLogin(OAuth2LoginDto oAuth2LoginDto) {
        String email = oAuth2LoginDto.getEmail();
        String platformType = oAuth2LoginDto.getPlatformType();
        User newUser = oAuth2LoginDto.toEntity(platformType);
        if (!userRepository.findByEmail(email).isPresent()) {
            userRepository.save(newUser);
        }
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        if (user.getPlatformType().toString().equals("NONE")) {
            throw new UserException(ErrorCode.MEMBER_WRONG_PLATFORM_TYPE);
        }
        String token = jwtTokenProvider.createToken(user.getId(), user.getRoles());
        return token;
    }

}
