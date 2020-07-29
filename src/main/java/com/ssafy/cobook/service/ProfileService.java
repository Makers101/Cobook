package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.follow.Follow;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.club.ClubResDto;
import com.ssafy.cobook.service.dto.profile.ProfileFollowUserDto;
import com.ssafy.cobook.service.dto.profile.ProfileResponseDto;
import com.ssafy.cobook.service.dto.user.UserByFollowDto;
import com.ssafy.cobook.service.dto.user.UserFollowResDto;
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
    private final ClubMemberRepository clubMemberRepository;
    private final FollowRepository followRepository;

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

        if(followRepository.findByToUser(fromUser, toUser).isPresent()){
            followRepository.deleteByUser(fromUser.getId(), toUser.getId());
            return;
        } else{
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
        followingList.addAll(followingList.size(),notFollowList);

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
        followerList.addAll(followerList.size(),notFollowList);

        return followerList;
    }
}

