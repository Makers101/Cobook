package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubevent.ClubEvent;
import com.ssafy.cobook.domain.clubevent.ClubEventRepository;
import com.ssafy.cobook.domain.clubeventmember.ClubEventMember;
import com.ssafy.cobook.domain.clubeventmember.ClubEventMemberRepository;
import com.ssafy.cobook.domain.clubeventquestion.ClubEventQuestion;
import com.ssafy.cobook.domain.clubeventquestion.ClubEventQuestionRepository;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.onedayeventquestion.OneDayEventQuestion;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.clubevent.ClubEventSaveReqDto;
import com.ssafy.cobook.service.dto.clubevent.ClubEventSaveResDto;
import com.ssafy.cobook.service.dto.clubevent.ClubEventUpdateReqDto;
import com.ssafy.cobook.service.dto.post.PostByMembersResDto;
import com.ssafy.cobook.service.dto.clubevent.ClubEventDetailResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubEventService {

    private final ClubEventRepository clubEventRepository;
    private final ClubRepository clubRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubEventMemberRepository clubEventMemberRepository;
    private final PostRepository postRepository;
    private final ClubEventQuestionRepository clubEventQuestionRepository;

    @Transactional
    public ClubEventSaveResDto makeReading(Long userId, Long clubId, ClubEventSaveReqDto reqDto) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        if (!clubMemberRepository.findByUserAndClub(user, club).isPresent()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        Book book = getBook(reqDto.getBookId());
        ClubEvent clubEvent = clubEventRepository.save(reqDto.toEntity());
        clubEvent.ofClub(club);
        clubEvent.ofBook(book);
        club.enrollReading(clubEvent);
        book.enrollReading(clubEvent);
        ClubEventMember clubEventMember = clubEventMemberRepository.save(new ClubEventMember(user, clubEvent, MemberRole.LEADER));
        user.enrollReading(clubEventMember);
        clubEvent.addMember(clubEventMember);
        List<ClubEventQuestion> questions = reqDto.getQuestions().stream()
                .map(q -> clubEventQuestionRepository.save(new ClubEventQuestion(clubEvent, q)))
                .collect(Collectors.toList());
        clubEvent.enrollQuestion(questions);
        return new ClubEventSaveResDto(clubEvent.getId());
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_BOOK));
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }

    private ClubEvent getClubEvent(Long clubEventId) {
        return clubEventRepository.findById(clubEventId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_READING));
    }

    public ClubEventDetailResDto getDetails(Long clubId, Long clubEventId, Long userId) {
        Club club = getClub(clubId);
        User user = getUser(userId);
        boolean isMember;
        isMember = clubMemberRepository.findByUserAndClub(user, club).isPresent();
        ClubEvent clubEvent = getClubEvent(clubEventId);
        if (!clubEvent.getClub().getId().equals(clubId)) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        List<ClubEventMember> members = clubEvent.getMembers();
        Book book = clubEvent.getBook();
        List<PostByMembersResDto> posts = getPosts(members, book);
        return new ClubEventDetailResDto(clubEvent, posts, isMember);
    }

    private List<PostByMembersResDto> getPosts(List<ClubEventMember> members, Book book) {
        List<PostByMembersResDto> ret = new ArrayList<>();
        members.stream()
                .map(m -> getPost(m.getUser(), book))
                .filter(Objects::nonNull)
                .map(PostByMembersResDto::getDto)
                .forEach(ret::addAll);
        return ret;
    }

    private List<Post> getPost(User user, Book book) {
        return postRepository.findAllByUserAndBook(user, book);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    @Transactional
    public void applyReading(Long clubEventId, Long clubId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        ClubEvent clubEvent = getClubEvent(clubEventId);
        ClubMember clubMember = clubMemberRepository.findByUserAndClub(user, club)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_READING));
        ClubEventMember readingMember = new ClubEventMember(user, clubEvent, MemberRole.MEMBER);
        if (clubEventMemberRepository.findByUserAndClubEvent(user, clubEvent).isPresent()) {
            ClubEventMember delete = clubEventMemberRepository.findByUserAndClubEvent(user, clubEvent).get();
            user.removeReading(delete);
            clubEvent.removeMember(delete);
            clubEventMemberRepository.delete(delete);
        } else {
            clubEventMemberRepository.save(readingMember);
            user.enrollReading(readingMember);
            clubEvent.addMember(readingMember);
        }
    }

    @Transactional
    public void updateEvent(Long userId, Long clubId, Long eventId, ClubEventUpdateReqDto reqDto) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        if (!clubMemberRepository.findByUserAndClub(user, club).isPresent()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        ClubEvent event = getClubEvent(eventId);
        if( !clubId.equals(event.getClub().getId())) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        ClubEventMember member = clubEventMemberRepository.findByUserAndClubEvent(user, event)
                .orElseThrow(()-> new BaseException(ErrorCode.ILLEGAL_ACCESS_READING));
        if( member.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        event.updateInfo(reqDto);
        Book book = getBook(reqDto.getBooKId());
        List<ClubEventQuestion> origin = event.getQuestions();
        clubEventQuestionRepository.deleteAll(origin);
        List<ClubEventQuestion> questions = reqDto.getQuestion().stream()
                .map(q -> clubEventQuestionRepository.save(new ClubEventQuestion(event, q)))
                .collect(Collectors.toList());
        event.setQuestions(questions);
        if (!book.getId().equals(event.getBook().getId())) {
            event.changeBook(book);
        }
    }

    @Transactional
    public void deleteEvents(Long eventId, Long clubId, Long userId) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        if (!clubMemberRepository.findByUserAndClub(user, club).isPresent()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        ClubEvent event = getClubEvent(eventId);
        if( !clubId.equals(event.getClub().getId())) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        ClubEventMember member = clubEventMemberRepository.findByUserAndClubEvent(user, event)
                .orElseThrow(()-> new BaseException(ErrorCode.ILLEGAL_ACCESS_READING));
        if( member.isNotLeader()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        club.removeEvents(event);
        List<ClubEventQuestion> questions = event.getQuestions();
        clubEventQuestionRepository.deleteAll(questions);
        event.delete();
        clubEventRepository.delete(event);
    }
}
