package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.follow.Follow;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.domain.usergenre.UserGenreRepository;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.club.ClubResDto;
import com.ssafy.cobook.service.dto.profile.ProfileFollowUserDto;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.user.UserByFollowDto;
import com.ssafy.cobook.service.dto.user.UserFollowResDto;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.service.dto.user.UserResponseIdDto;
import com.ssafy.cobook.service.dto.user.UserUpdateReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProfileService {

    private final String IMAGE_DIR = "/home/ubuntu/images/profile/";
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final FollowRepository followRepository;
    private final GenreRepository genreRepository;
    private final UserGenreRepository userGenreRepository;
    
    public ProfileResponseDto getUserInfo(Long fromUserId, Long toUserId) {
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        List<ClubResDto> clubList = clubMemberRepository.findAllByUser(toUser).stream()
                .map(ClubMember::getClub)
                .map(ClubResDto::new)
                .collect(Collectors.toList());

        UserFollowResDto to = new UserFollowResDto(toUser.getId(), toUser.getNickName());
        UserFollowResDto from = new UserFollowResDto(fromUser.getId(), fromUser.getNickName());


        ProfileFollowUserDto profileFollowUserDto = new ProfileFollowUserDto(to, from, false);

        List<UserByFollowDto> followerList = getFollowerList(profileFollowUserDto);
        List<UserByFollowDto> followingList = getFollowingList(profileFollowUserDto);

        ProfileResponseDto profileResponseDto = new ProfileResponseDto(toUser, clubList, followerList, followingList);

        return profileResponseDto;
    }

    private User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));
    }

    // 팔로잉 - 눌러져있지 않으면 디비에 추가한다
    public void addFollow(ProfileFollowUserDto profileFollowUserDto) {
        User toUser = userRepository.findById(profileFollowUserDto.getToUser().getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        User fromUser = userRepository.findById(profileFollowUserDto.getFromUser().getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        if (followRepository.findByToUser(fromUser, toUser).isPresent()) {
            followRepository.deleteByUser(fromUser.getId(), toUser.getId());
            return;
        } else {
            followRepository.save(new Follow(fromUser, toUser, false));
        }
    }

    // 팔로잉 리스트를 가져온다
    public List<UserByFollowDto> getFollowingList(ProfileFollowUserDto profileFollowUserDto) {
        User toUser = userRepository.findById(profileFollowUserDto.getToUser().getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        User fromUser = userRepository.findById(profileFollowUserDto.getFromUser().getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        // 1. isFollow인 애 찾기
        List<UserByFollowDto> followList = followRepository.findAllByFollowing(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getToUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList());

        followList.forEach(userByFollowDto -> userByFollowDto.setIsFollow(true));

        // 2. isNotFollow 애 찾기
        List<UserByFollowDto> notFollowList = followRepository.findAllByNotFollowing(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getToUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList());

        List<UserByFollowDto> followingList = new ArrayList<>();
        followingList.addAll(0, followList);
        followingList.addAll(0, notFollowList);

        return followingList;
    }

    // 팔로워 리스트 가져오기 (fromUser의 아이디가 들어오면 toUser가 fromUser의 값인 애들을 뽑아야함)
    public List<UserByFollowDto> getFollowerList(ProfileFollowUserDto profileFollowUserDto) {
        User toUser = userRepository.findById(profileFollowUserDto.getToUser().getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        User fromUser = userRepository.findById(profileFollowUserDto.getFromUser().getUserId())
                .orElseThrow(() -> new UserException(ErrorCode.UNSIGNED));

        // 1. isFollow인 애 찾기
        List<UserByFollowDto> followList = followRepository.findAllByFollower(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getFromUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList());

        followList.forEach(userByFollowDto -> userByFollowDto.setIsFollow(true));

        // 2. isNotFollow 애 찾기
        List<UserByFollowDto> notFollowList = followRepository.findAllByNotFollower(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getFromUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList());

        List<UserByFollowDto> followerList = new ArrayList<>();
        followerList.addAll(0, followList);
        followerList.addAll(0, notFollowList);

        return followerList;
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
        for (Genre genre : updateGenres) {
            updateUserGenres(user, genre);
        }
        user.updateInfo(requestDto.getNickName(), requestDto.getDescription());
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