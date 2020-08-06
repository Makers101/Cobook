package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.clubeventmember.ClubEventMember;
import com.ssafy.cobook.domain.onedayevent.OneDayEvent;
import com.ssafy.cobook.domain.onedayevent.OneDayEventRepository;
import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMember;
import com.ssafy.cobook.domain.onedayeventmember.OneDayEventMemberRepository;
import com.ssafy.cobook.domain.onedayeventquestion.OneDayEventQuestion;
import com.ssafy.cobook.domain.onedayeventquestion.OneDayEventQuestionRepository;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.clubevent.ClubEventDetailResDto;
import com.ssafy.cobook.service.dto.onedayevent.*;
import com.ssafy.cobook.service.dto.post.PostByMembersResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.ssafy.cobook.domain.clubmember.MemberRole.LEADER;
import static com.ssafy.cobook.domain.clubmember.MemberRole.MEMBER;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OneDayEventService {

    private final OneDayEventRepository eventRepository;
    private final OneDayEventMemberRepository memberRepository;
    private final OneDayEventQuestionRepository questionRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final PostRepository postRepository;

    @Transactional
    public OneDayEventSaveResDto saveEvent(Long userId, OneDayEventSaveReqDto requestDto) {
        if (eventRepository.findByTitle(requestDto.getName()).isPresent()) {
            throw new BaseException(ErrorCode.EXIST_CLUB_NAME);
        }
        User user = getUser(userId);
        Book book = getBook(requestDto.getBookId());
        OneDayEvent oneDayEvent = requestDto.toEntity();
        oneDayEvent.setBook(book);
        oneDayEvent = eventRepository.save(oneDayEvent);
        OneDayEventMember member = memberRepository.save(new OneDayEventMember(user, oneDayEvent, LEADER));
        oneDayEvent.addMember(member);
        user.enrollOneDayEvent(member);
        book.addEvent(oneDayEvent);
        OneDayEvent finalOneDayEvent = oneDayEvent;
        List<OneDayEventQuestion> questions = requestDto.getQuestions().stream()
                .map(q -> questionRepository.save(new OneDayEventQuestion(finalOneDayEvent, q)))
                .collect(Collectors.toList());
        oneDayEvent.setQuestions(questions);
        return new OneDayEventSaveResDto(oneDayEvent.getId());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_BOOK));
    }

    public List<OneDayEventResponseDto> getAllEvents() {
        return eventRepository.findAll().stream()
                .filter(e-> !e.getClosed())
                .map(OneDayEventResponseDto::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public OneDayEvent getEvent(Long eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_EVENT));
    }

    public OneDayEventDetailsDto getDetails(Long userId, Long eventId) {
        User user = getUser(userId);
        OneDayEvent event = getEvent(eventId);
        boolean isMember = memberRepository.findByUserAndOneDayEvent(user, event).isPresent();
        List<OneDayEventMember> members = event.getMembers();
        Book book = event.getBook();
        List<PostByMembersResDto> posts = getPosts(members, book);
        return new OneDayEventDetailsDto(event, posts, isMember);

    }

    private List<PostByMembersResDto> getPosts(List<OneDayEventMember> members, Book book) {
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

    @Transactional
    public void applyEvent(Long userId, Long eventId) {
        User user = getUser(userId);
        OneDayEvent event = getEvent(eventId);
        if (memberRepository.findByUserAndOneDayEvent(user, event).isPresent()) {
            OneDayEventMember member = memberRepository.findByUserAndOneDayEvent(user, event).get();
            user.removeEvents(member);
            event.removeMember(member);
            memberRepository.delete(member);
        } else {
            OneDayEventMember member = memberRepository.save(new OneDayEventMember(user, event, MEMBER));
            user.enrollOneDayEvent(member);
            event.addMember(member);
        }
    }

    @Transactional
    public void updatePost(Long userId, Long eventId, OneDayEventUpdateReqDto requestDto) {
        User user = getUser(userId);
        OneDayEvent event = getEvent(eventId);
        OneDayEventMember member = memberRepository.findByUserAndOneDayEvent(user, event)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_EVENT));
        if (member.isNotLeader()) {
            throw new BaseException(ErrorCode.UNEXPECTED_EVENT);
        }
        event.updateInfo(requestDto);
        Book book = getBook(requestDto.getBookId());
        questionRepository.deleteAll(event.getQuestions());
        List<OneDayEventQuestion> questions = requestDto.getQuestions().stream()
                .map(q -> questionRepository.save(new OneDayEventQuestion(event, q)))
                .collect(Collectors.toList());
        event.setQuestions(questions);
        if (!book.getId().equals(event.getBook().getId())) {
            event.chageBook(book);
        }
    }

    @Transactional
    public void deleteEvents(Long userId, Long eventId) {
        User user = getUser(userId);
        OneDayEvent event = getEvent(eventId);
        OneDayEventMember member = memberRepository.findByUserAndOneDayEvent(user, event)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_EVENT));
        if (member.isNotLeader()) {
            throw new BaseException(ErrorCode.UNEXPECTED_EVENT);
        }
        List<OneDayEventQuestion> questions = event.getQuestions();
        questionRepository.deleteAll(questions);
        Book book = event.getBook();
        book.removeEvent(event);
        List<OneDayEventMember> members = event.getMembers();
        members.forEach(OneDayEventMember::signOutEvent);
        memberRepository.deleteAll(members);
        eventRepository.delete(event);
    }
}
