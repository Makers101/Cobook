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
import com.ssafy.cobook.domain.usergenre.UserGenreRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.club.ClubResDto;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.user.UserResponseIdDto;
import com.ssafy.cobook.service.dto.user.UserUpdateReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final String IMAGE_DIR = "/home/ubuntu/images/profile/";

    private final UserRepository userRepository;
    private final GenreRepository genreRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final UserGenreRepository userGenreRepository;

    public ProfileResponseDto getUserInfo(Long id) {
        User user = getUserById(id);
        List<ClubResDto> clubList = clubMemberRepository.findAllByUser(user).stream()
                .map(ClubMember::getClub)
                .map(ClubResDto::new)
                .collect(Collectors.toList());
        return new ProfileResponseDto(user, clubList);
    }

    private User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));
    }

    private Genre getGenre(final Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_GENRE));
    }

    private UserGenre getUserGenre(User user, Genre genre) {
        return userGenreRepository.findByUserAndGenre(user, genre)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED));
    }

    @Transactional
    public UserResponseIdDto updateUserInfo(Long userId, UserUpdateReqDto requestDto) {
        User user = getUserById(userId);
        List<Genre> updateGenres = requestDto.getGenres().stream()
                .map(this::getGenre)
                .collect(Collectors.toList());
        List<Genre> userGenres = user.getUserGenres().stream()
                .map(UserGenre::getGenre)
                .collect(Collectors.toList());
        for (Genre genre : userGenres) {
            if (updateGenres.contains(genre)) {
                updateGenres.remove(genre);
                continue;
            }
            UserGenre userGenre = getUserGenre(user, genre);
            user.removeGenre(userGenre);
            genre.removeUser(userGenre);
            userGenreRepository.delete(userGenre);
        }
        for(Genre genre : updateGenres) {
            updateUserGenres(user, genre);
        }
        user.updateInfo(requestDto.getNicnName(), requestDto.getDescription());
        return new UserResponseIdDto(user.getId());
    }

    private void updateUserGenres(User user, Genre genre) {
        UserGenre userGenre = userGenreRepository.save(new UserGenre(user, genre));
        user.addGenres(userGenre);
        genre.addUser(userGenre);
    }

    @Transactional
    public void saveImg(Long userId, MultipartFile profileImg) throws IOException {
        uploadFile(profileImg);
        User user = getUserById(userId);
        user.setProfile("http://i3a111.p.ssafy.io:8080/api/profile/images/profile/" + profileImg.getOriginalFilename());
    }

    private void uploadFile(MultipartFile file) throws IOException {
        String originName = file.getOriginalFilename();
        File dest = new File(IMAGE_DIR + originName);
        file.transferTo(dest);
    }

    public String getFilePath(Long userId) {
        User user = getUserById(userId);
        return user.getProfileImg().replace("http://i3a111.p.ssafy.io:8080/api/profile/images/", "");
    }
}

