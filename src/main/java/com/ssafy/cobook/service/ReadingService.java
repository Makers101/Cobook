package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.reading.Reading;
import com.ssafy.cobook.domain.reading.ReadingRepository;
import com.ssafy.cobook.domain.readingmember.ReadingMember;
import com.ssafy.cobook.domain.readingmember.ReadingMemberRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.post.PostSimpleResDto;
import com.ssafy.cobook.service.dto.question.QuestionResDto;
import com.ssafy.cobook.service.dto.reading.ReadingApplyReqDto;
import com.ssafy.cobook.service.dto.reading.ReadingDetailResDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveReqDto;
import com.ssafy.cobook.service.dto.reading.ReadingSaveResDto;
import com.ssafy.cobook.service.dto.user.UserSimpleResDto;
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
    private final ReadingMemberRepository readingMemberRepository;
    private final PostRepository postRepository;

    @Transactional
    public ReadingSaveResDto makeReading(ReadingSaveReqDto reqDto) {
        Club club = getClub(reqDto.getClubId());
        Book book = getBook(reqDto.getBookId());
        Reading reading = readingRepository.save(reqDto.toEntity());
        reading.ofClub(club);
        reading.ofBook(book);
        club.enrollReading(reading);
        book.enrollReading(reading);
        return new ReadingSaveResDto(reading.getId());
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(()->new BaseException(ErrorCode.UNEXPECTED_BOOK));
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }

    private Reading getReading(Long readingId) {
        return readingRepository.findById(readingId)
                .orElseThrow(()->new BaseException(ErrorCode.UNEXPECTED_READING));
    }

    public ReadingDetailResDto getDetails(Long clubId, Long readingId) {
        Club club = getClub(clubId);
        Reading reading = getReading(readingId);
        if(!reading.getClub().getId().equals(clubId)) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        List<ReadingMember> members = reading.getMembers();
        List<UserSimpleResDto> users = members.stream()
                .map(m -> new UserSimpleResDto(m.getUser(), m.getRole()))
                .collect(Collectors.toList());
        List<QuestionResDto> questions = reading.getQuestions().stream()
                .map(q->new QuestionResDto(q.getId(), q.getQuestion()))
                .collect(Collectors.toList());
        Book book = reading.getBook();
        List<PostSimpleResDto> posts = getReadingPosts(members, book);
        return new ReadingDetailResDto(reading, users, questions, posts);
    }

    private List<PostSimpleResDto> getReadingPosts(List<ReadingMember> members, Book book) {
        return members.stream()
                .map(m->getPost(m.getUser(), book))
                .map(PostSimpleResDto::new)
                .collect(Collectors.toList());
    }

    private Post getPost(User user, Book book) {
        return postRepository.findByUserAndBook(user, book)
                .orElse(null);
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(()->new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    @Transactional
    public void applyReading(ReadingApplyReqDto reqDto) {
        Club club = getClub(reqDto.getClubId());
        Reading reading = getReading(reqDto.getReadingId());
        if( !reading.getClub().getId().equals(club.getId())) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_READING);
        }
        User user = getUser(reqDto.getUserId());
        ReadingMember readingMember = readingMemberRepository.save(new ReadingMember(user, reading, MemberRole.MEMBER));
    }
}
