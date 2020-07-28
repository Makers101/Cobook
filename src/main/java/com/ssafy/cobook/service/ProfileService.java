package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class ProfileService {
    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
    private final GenreRepository genreRepository;

//    @Transactional
//    public UserResponseIdDto updateUserInfo(Long userid, UserUpdateDto userUpdateDto) {
//        User user = userRepository.findById(userid)
//                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));
//
//        List<UserGenre> userGenreList = userUpdateDto.getGenreList().stream()
//                .map(s -> getGenres(s.getId()))
//                .map(g -> new UserGenre(user, g))
//                .collect(Collectors.toList());
//        user.updateUserInfo(userUpdateDto, userGenreList);
//        return new UserResponseIdDto(user.getId());
//    }

//    public Genre getGenres(Long genreId) {
//        return genreRepository.findById(genreId)
//                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_GENRE));
//    }

    public ProfileResponseDto getUserInfo(Long id) {
        User user = getUserById(id);
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(user);
        return profileResponseDto;
    }

    private User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));
    }

}

