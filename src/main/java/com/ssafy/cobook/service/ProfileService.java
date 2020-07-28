package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.book.BookResponseDto;
import com.ssafy.cobook.service.dto.club.ClubResDto;
import com.ssafy.cobook.service.dto.clubmember.ClubMemberDto;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor

public class ProfileService {
    private final UserRepository userRepository;
    //    private final PasswordEncoder passwordEncoder;
    private final GenreRepository genreRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
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
        List<ClubResDto> clubList = clubMemberRepository.findAllByUser(user).stream()
                .map(ClubMember::getClub)
                .map(ClubResDto::new)
                .collect(Collectors.toList());
        ProfileResponseDto profileResponseDto = new ProfileResponseDto(user, clubList);

        return profileResponseDto;
    }


    private User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));
    }

}

