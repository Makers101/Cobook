package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.clubevent.ClubEventRepository;
import com.ssafy.cobook.domain.clubeventmember.ClubEventMember;
import com.ssafy.cobook.domain.clubeventmember.ClubEventMemberRepository;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.follow.Follow;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.domain.onedayevent.OneDayEventRepository;
import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMember;
import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMemberRepository;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.postbookmark.PostBookMark;
import com.ssafy.cobook.domain.postbookmark.PostBookMarkRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.domain.usergenre.UserGenre;
import com.ssafy.cobook.domain.usergenre.UserGenreRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.club.ClubResDto;
import com.ssafy.cobook.service.dto.clubevent.ClubEventSimpleResDto;
import com.ssafy.cobook.service.dto.genre.GenreResponseDto;
import com.ssafy.cobook.service.dto.onedayevent.OneDayEventResponseDto;
import com.ssafy.cobook.service.dto.post.PostResponseDto;
import com.ssafy.cobook.service.dto.profile.ProfileByStatisticsForGenre;
import com.ssafy.cobook.service.dto.profile.ProfileByStatisticsForPeriod;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.profile.ProfileStatisticsResDto;
import com.ssafy.cobook.service.dto.user.UserByFollowDto;
import com.ssafy.cobook.service.dto.user.UserResponseIdDto;
import com.ssafy.cobook.service.dto.user.UserUpdateReqDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
    private final PostRepository postRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final ClubEventMemberRepository clubEventMemberRepository;
    private final OneDayEventMemberRepository oneDayEventMemberRepository;


    public ProfileResponseDto getUserInfo(Long fromUserId, Long toUserId) {
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        List<ClubResDto> clubList = clubMemberRepository.findAllByUser(toUser).stream()
                .filter(m -> !m.getRole().equals(MemberRole.WAITING))
                .map(ClubMember::getClub)
                .map(ClubResDto::new)
                .collect(Collectors.toList());

        List<UserByFollowDto> followerList = getFollowerList(fromUserId, toUserId);
        List<UserByFollowDto> followingList = getFollowingList(fromUserId, toUserId);

        ProfileResponseDto profileResponseDto = new ProfileResponseDto(toUser, clubList, followerList, followingList);

        return profileResponseDto;
    }

    private User getUserById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));
    }

    // 팔로잉 - 눌러져있지 않으면 디비에 추가한다
    public void addFollow(Long fromUserId, Long toUserId) {
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));


        if (followRepository.findByToUser(fromUser, toUser).isPresent()) {
            followRepository.deleteByUser(fromUser.getId(), toUser.getId());
            return;
        } else {
            followRepository.save(new Follow(fromUser, toUser, false));
        }
    }

    // 팔로잉 리스트를 가져온다
    public List<UserByFollowDto> getFollowingList(Long fromUserId, Long toUserId) {


        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        // 1. isFollow인 애 찾기
        List<UserByFollowDto> followList = Optional.ofNullable(followRepository.findAllByFollowing(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getToUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        followList.forEach(userByFollowDto -> userByFollowDto.setIsFollow(true));

        // 2. isNotFollow 애 찾기
        List<UserByFollowDto> notFollowList = Optional.ofNullable(followRepository.findAllByNotFollowing(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getToUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList())).orElse(Collections.emptyList());

        List<UserByFollowDto> followingList = new ArrayList<>();
        if (!followList.isEmpty()) {
            followingList.addAll(0, followList);
        }
        if (!notFollowList.isEmpty()) {
            followingList.addAll(0, notFollowList);
        }

        return followingList;
    }

    // 팔로워 리스트 가져오기 (fromUser의 아이디가 들어오면 toUser가 fromUser의 값인 애들을 뽑아야함)
    public List<UserByFollowDto> getFollowerList(Long fromUserId, Long toUserId) {
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        User fromUser = userRepository.findById(fromUserId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        // 1. isFollow인 애 찾기
        List<UserByFollowDto> followList = Optional.ofNullable(followRepository.findAllByFollower(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getFromUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        followList.forEach(userByFollowDto -> userByFollowDto.setIsFollow(true));

        // 2. isNotFollow 애 찾기
        List<UserByFollowDto> notFollowList = Optional.ofNullable(followRepository.findAllByNotFollower(toUser.getId(), fromUser.getId())
                .stream()
                .map(Follow::getFromUser)
                .map(UserByFollowDto::new)
                .collect(Collectors.toList()))
                .orElse(Collections.emptyList());

        List<UserByFollowDto> followerList = new ArrayList<>();
        if (!followerList.isEmpty()) {
            followerList.addAll(0, followList);
        }
        if (!notFollowList.isEmpty()) {
            followerList.addAll(0, notFollowList);
        }

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
        user.setProfile("profile/" + profileImg.getOriginalFilename());
    }

    private void uploadFile(MultipartFile file) throws IOException {
        String originName = file.getOriginalFilename();
        File dest = new File(IMAGE_DIR + originName);
        file.transferTo(dest);
    }

    public String getFilePath(Long userId) {
        User user = getUserById(userId);
        return user.getProfileImg();
    }

    public List<PostResponseDto> getUserFeed(Long userId) {
        User user = getUserById(userId);
        return postRepository.findAllByUser(user)
                .stream()
                .filter(Post::getOpen)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ClubResDto> getUserClub(Long userId) {
        User user = getUserById(userId);

        return clubMemberRepository.findAllByUser(user).stream()
                .filter(c->!c.getRole().equals(MemberRole.WAITING))
                .map(ClubMember::getClub)
                .map(ClubResDto::new)
                .collect(Collectors.toList());
    }

    public List<ClubEventSimpleResDto> getClubEvents(Long userId) {
        User user = getUserById(userId);

        return clubEventMemberRepository.findByUser(user)
                .stream()
                .map(ClubEventMember::getClubEvent)
                .map(ClubEventSimpleResDto::new)
                .collect(Collectors.toList());
    }

    public List<OneDayEventResponseDto> getOneDayEvents(Long userId) {
        User user = getUserById(userId);


        return oneDayEventMemberRepository.findByUser(user).stream()
                .map(OneDayEventMember::getOneDayEvent)
                .map(OneDayEventResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostResponseDto> getUserBookmark(Long userId) {
        User user = getUserById(userId);
        return postBookMarkRepository.findAllByUser(user)
                .stream()
                .map(PostBookMark::getPost)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }


    public ProfileStatisticsResDto getUserStatics(Long userId) {
        User user = getUserById(userId);

        // 장르별 정보
        List<GenreResponseDto> genreList = postRepository.findAllByUser(user).stream()
                .map(Post::getBook)
                .map(Book::getGenre)
                .map(GenreResponseDto::new)
                .collect(Collectors.toList());

        Map<String, Long> genreData = new TreeMap<>();

        for (GenreResponseDto g : genreList) {
            if (!genreData.containsKey(g.getName())) {
                genreData.put(g.getName(), 1L);
            } else {
                Long count = genreData.get(g.getName());
                genreData.put(g.getName(), count + 1);
            }
        }

        List<ProfileByStatisticsForGenre> genresByStatistics = new ArrayList<>();

        for (String key : genreData.keySet()) {
            genresByStatistics.add(new ProfileByStatisticsForGenre(key, genreData.get(key)));
        }
        genresByStatistics.sort((ProfileByStatisticsForGenre data1, ProfileByStatisticsForGenre data2)
                -> data2.getCount().compareTo(data1.getCount()));

        // 기간별 정보
        List<PostResponseDto> periodsDate = postRepository.findAllByUser(user).stream()
                .map(PostResponseDto::new)
                .collect(Collectors.toList());

        Map<String, Long> periodData = new TreeMap<>();

        LocalDate curDate = LocalDate.now(); // 현재 날짜
        String[] preDate = new String[12];
        preDate[0] = curDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

        periodData.put(preDate[0], 0L);

        for (int month = 1; month < 12; month++) {
            preDate[month] = curDate.minusMonths(month).format(DateTimeFormatter.ofPattern("yyyy-MM"));
            periodData.put(preDate[month], 0L);
        }

        for (PostResponseDto p : periodsDate) {
            LocalDate postLocalDate = LocalDate.from(p.getCreatedAt());
            String postStringDate = postLocalDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            for (int month = 0; month < 12; month++) {
                if (preDate[month].equals(postStringDate)) {
                    String periods = preDate[month];
                    Long count = periodData.get(periods);
                    periodData.put(periods, count + 1);
                    break;
                }
            }
        }


        List<ProfileByStatisticsForPeriod> periodsByStatistics = new ArrayList<>();

        for (String key : periodData.keySet()) {
            periodsByStatistics.add(new ProfileByStatisticsForPeriod(key, periodData.get(key)));
        }

        ProfileStatisticsResDto profileStaticsResDto = new ProfileStatisticsResDto(genresByStatistics, periodsByStatistics);

        return profileStaticsResDto;
    }
}