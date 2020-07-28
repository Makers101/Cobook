package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.reading.Reading;
import com.ssafy.cobook.domain.reading.ReadingRepository;
import com.ssafy.cobook.domain.readingmember.ReadingMember;
import com.ssafy.cobook.domain.readingmember.ReadingMemberRepository;
import com.ssafy.cobook.domain.readingquestion.ReadingQuestion;
import com.ssafy.cobook.domain.readingquestion.ReadingQuestionRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.post.PostByMembersResDto;
import com.ssafy.cobook.service.dto.post.PostSimpleResDto;
import com.ssafy.cobook.service.dto.reading.ReadingApplyReqDto;
import com.ssafy.cobook.service.dto.reading.ReadingDetailResDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveReqDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReadingService {

    private final ReadingRepository readingRepository;
    private final ClubRepository clubRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ReadingMemberRepository readingMemberRepository;
    private final PostRepository postRepository;
    private final ReadingQuestionRepository readingQuestionRepository;

    @Transactional
    public ReadingSaveResDto makeReading(Long userId, Long clubId, ReadingSaveReqDto reqDto) {
        User user = getUser(userId);
        Club club = getClub(clubId);
        if (!clubMemberRepository.findByUserAndClub(user, club).isPresent()) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        Book book = getBook(reqDto.getBookId());
        Reading reading = readingRepository.save(reqDto.toEntity());
        reading.ofClub(club);
        reading.ofBook(book);
        club.enrollReading(reading);
        book.enrollReading(reading);
        ReadingMember readingMember = readingMemberRepository.save(new ReadingMember(user, reading, MemberRole.LEADER));
        user.enrollReading(readingMember);
        reading.addMember(readingMember);
        List<ReadingQuestion> questions = reqDto.getQuestions().stream()
                .map(q -> readingQuestionRepository.save(new ReadingQuestion(reading, q)))
                .collect(Collectors.toList());
        reading.enrollQuestion(questions);
        return new ReadingSaveResDto(reading.getId());
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_BOOK));
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }

    private Reading getReading(Long readingId) {
        return readingRepository.findById(readingId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_READING));
    }

    public ReadingDetailResDto getDetails(Long clubId, Long readingId) {
        Club club = getClub(clubId);
        Reading reading = getReading(readingId);
        if (!reading.getClub().getId().equals(clubId)) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        List<ReadingMember> members = reading.getMembers();
        Book book = reading.getBook();
        List<PostByMembersResDto> posts = getPosts(members, book);
        return new ReadingDetailResDto(reading, posts);
    }

    private List<PostByMembersResDto> getPosts(List<ReadingMember> members, Book book) {
        return members.stream()
                .map(m -> getPost(m.getUser(), book))
                .map(PostByMembersResDto::new)
                .collect(Collectors.toList());
    }

    private List<PostSimpleResDto> getReadingPosts(List<ReadingMember> members, Book book) {
        return members.stream()
                .map(m -> getPost(m.getUser(), book))
                .map(PostSimpleResDto::new)
                .collect(Collectors.toList());
    }

    private Post getPost(User user, Book book) {
        return postRepository.findByUserAndBook(user, book)
                .orElse(null);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    @Transactional
    public void applyReading(ReadingApplyReqDto reqDto) {
        Club club = getClub(reqDto.getClubId());
        Reading reading = getReading(reqDto.getReadingId());
        if (!reading.getClub().getId().equals(club.getId())) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        User user = getUser(reqDto.getUserId());
        ReadingMember readingMember = readingMemberRepository.save(new ReadingMember(user, reading, MemberRole.MEMBER));
    }
}
