package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubevent.ClubEvent;
import com.ssafy.cobook.domain.clubevent.ClubEventRepository;
import com.ssafy.cobook.domain.clubeventquestion.ClubEventQuestion;
import com.ssafy.cobook.domain.clubeventquestion.ClubEventQuestionRepository;
import com.ssafy.cobook.domain.clubgenre.ClubGenre;
import com.ssafy.cobook.domain.clubgenre.ClubGenreRepository;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.follow.Follow;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.genre.Genre;
import com.ssafy.cobook.domain.genre.GenreRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.club.*;
import com.ssafy.cobook.service.dto.user.UserSimpleResDto;
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
public class ClubService {

    private final String IMAGE_DIR = "/home/ubuntu/images/club/";
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final GenreRepository genreRepository;
    private final ClubGenreRepository clubGenreRepository;
    private final FollowRepository followRepository;
    private final ClubEventRepository clubEventRepository;
    private final ClubEventQuestionRepository questionRepository;

    @Transactional
    public ClubCreateResDto create(Long userId, ClubCreateReqDto reqDto) throws IOException {
        if (clubRepository.findByName(reqDto.getName()).isPresent()) {
            throw new BaseException(ErrorCode.EXIST_CLUB_NAME);
        }
        User user = getUser(userId);
        Club club = clubRepository.save(reqDto.toEntity());
        ClubMember leader = clubMemberRepository.save(new ClubMember(user, club, MemberRole.LEADER));
        club.enrolls(leader);
        List<Genre> genres = reqDto.getGenres().stream()
                .map(this::getGenre)
                .collect(Collectors.toList());
        List<ClubGenre> clubGenres = genres.stream()
                .map(g -> clubGenreRepository.save(new ClubGenre(club, g)))
                .collect(Collectors.toList());
        club.setGenres(clubGenres);
        return new ClubCreateResDto(club.getId());
    }

    private Genre getGenre(Long id) {
        return genreRepository.findById(id)
                .orElseThrow(() -> new BaseException(ErrorCode.INVALID_GENRE));
    }

    private void uploadFile(MultipartFile file) throws IOException {
        String originName = file.getOriginalFilename();
        File dest = new File(IMAGE_DIR + originName);
        file.transferTo(dest);
    }

    @Transactional
    public void joinClub(Long userId, Long clubId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        if (clubMemberRepository.findByUserAndClub(user, club).isPresent()) {
            ClubMember clubMember = clubMemberRepository.findByUserAndClub(user, club).get();
            if (clubMember.getRole().equals(MemberRole.WAITING)) {
                clubMemberRepository.delete(clubMemberRepository.findByUserAndClub(user, club).get());
            }
            return;
        }
        clubMemberRepository.save(new ClubMember(user, club, MemberRole.WAITING));
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }

    public List<ClubResDto> getClubList() {
        return clubRepository.findAll().stream()
                .map(ClubResDto::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public ClubDetailResDto getClubDetail(Long clubId) {
        Club club = getClub(clubId);
        List<UserSimpleResDto> users = club.getMembers()
                .stream()
                .map(c -> new UserSimpleResDto(c.getUser(), c.getRole()))
                .collect(Collectors.toList());
        return new ClubDetailResDto(club);
    }

    @Transactional
    public void fileSave(Long clubId, MultipartFile clubImg) throws IOException {
        uploadFile(clubImg);
        Club club = getClub(clubId);
        club.setProfile("club/" + clubImg.getOriginalFilename());
    }

    public String getFilePath(Long id) {
        Club club = getClub(id);
        return club.getClubImg();
    }

    @Transactional
    public void approve(Long clubId, Long clubMemberId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubMember leader = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB));
        if (leader.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB);
        }
        ClubMember waiting = clubMemberRepository.findById(clubMemberId)
                .orElseThrow(() -> new BaseException(ErrorCode.ALREADY_PROCESS));
        if (!waiting.onWait()) {
            throw new BaseException(ErrorCode.ALREADY_PROCESS);
        }
        waiting.changeRole(MemberRole.MEMBER);
        User clubMember = waiting.getUser();
        clubMember.enrollClub(waiting);
        club.enrolls(waiting);
    }

    @Transactional
    public void reject(Long clubId, Long clubMemberId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubMember leader = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB));
        if (leader.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB);
        }
        ClubMember waiting = clubMemberRepository.findById(clubMemberId)
                .orElseThrow(() -> new BaseException(ErrorCode.ALREADY_PROCESS));
        if (!waiting.onWait()) {
            throw new BaseException(ErrorCode.ALREADY_PROCESS);
        }
        clubMemberRepository.delete(waiting);
    }

    @Transactional
    public ClubRecruitResponseDto changeRecruit(Long clubId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubMember leader = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB));
        if (leader.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB);
        }
        club.changeRecruit();
        return new ClubRecruitResponseDto(club.getRecruit());
    }

    private Long getClubFollow(Club club) {
        List<Follow> list = followRepository.findAllByClub(club);
        return Long.valueOf(list.size());
    }

    public ClubByFollowSimpleDto addFollow(Long userId, Long clubId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new UserException(ErrorCode.UNEXPECTED_USER));

        Boolean isFollow = false;

        if (followRepository.findByFromUserAndClub(user, club).isPresent()) {
            followRepository.deleteByUserAndClub(userId, clubId);
        } else {
            followRepository.save(new Follow(user, club, true));
            isFollow = true;
        }

        Long userCount = getClubFollow(club);

        return new ClubByFollowSimpleDto(userId, isFollow, userCount);
    }

    public List<ClubCandidatesResponseDto> getCandidates(Long clubId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubMember clubMember = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB));
        if (clubMember.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB);
        }
        return club.getMembers().stream()
                .filter(m -> m.getRole().onWait())
                .map(ClubCandidatesResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void signOutClub(Long clubId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubMember clubMember = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB));
        user.removeClub(clubMember);
        club.removeMember(clubMember);
        clubMemberRepository.delete(clubMember);
    }

    @Transactional
    public void deleteClub(Long clubId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubMember leader = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB));
        if (user.getNickName().equals("코북이")) {
            List<ClubMember> members = club.getMembers();
            for (ClubMember clubMember : members) {
                clubMember.removeUser();
            }
            clubMemberRepository.deleteAll(members);
            List<ClubEvent> events = club.getClubEvents();
            for (ClubEvent clubEvent : events) {
                List<ClubEventQuestion> questions = clubEvent.getQuestions();
                questionRepository.deleteAll(questions);
                clubEvent.delete();
            }
            clubEventRepository.deleteAll(events);
            List<ClubGenre> genres = club.getGenres();
            for (ClubGenre clubGenre : genres) {
                clubGenre.remove();
            }
            clubGenreRepository.deleteAll(genres);
            clubRepository.delete(club);
            return;
        }
        if (leader.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB);
        }
        List<ClubMember> members = club.getMembers();
        for (ClubMember clubMember : members) {
            clubMember.removeUser();
        }
        clubMemberRepository.deleteAll(members);
        List<ClubEvent> events = club.getClubEvents();
        for (ClubEvent clubEvent : events) {
            List<ClubEventQuestion> questions = clubEvent.getQuestions();
            questionRepository.deleteAll(questions);
            clubEvent.delete();
        }
        clubEventRepository.deleteAll(events);
        List<ClubGenre> genres = club.getGenres();
        for (ClubGenre clubGenre : genres) {
            clubGenre.remove();
        }
        clubGenreRepository.deleteAll(genres);
        clubRepository.delete(club);
    }

    @Transactional
    public void updateClub(Long userId, Long clubId, ClubUpdateRequestDto requestDto) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubMember leader = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB));
        if (leader.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_CLUB);
        }
        club.updateInfos(requestDto);
        List<ClubGenre> originGenres = club.getGenres();
        clubGenreRepository.deleteAll(originGenres);
        List<Genre> genres = requestDto.getGenres().stream()
                .map(this::getGenre)
                .collect(Collectors.toList());
        for (ClubGenre genre : originGenres) {
            Genre temp = genre.getGenre();
            temp.removeClub(genre);
        }
        List<ClubGenre> clubGenres = genres.stream()
                .map(g -> saveGenre(club, g))
                .collect(Collectors.toList());
        club.addGenres(clubGenres);
    }

    private ClubGenre saveGenre(Club club, Genre g) {
        return clubGenreRepository.save(new ClubGenre(club, g));
    }
}