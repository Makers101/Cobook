package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.book.Book;
import com.ssafy.cobook.domain.book.BookRepository;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.follow.Follow;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.postbookmark.PostBookMark;
import com.ssafy.cobook.domain.postbookmark.PostBookMarkRepository;
import com.ssafy.cobook.domain.postcomment.PostComment;
import com.ssafy.cobook.domain.postcomment.PostCommentRepository;
import com.ssafy.cobook.domain.postlike.PostLike;
import com.ssafy.cobook.domain.postlike.PostLikeRepository;
import com.ssafy.cobook.domain.posttag.PostTag;
import com.ssafy.cobook.domain.posttag.PostTagRepository;
import com.ssafy.cobook.domain.tag.Tag;
import com.ssafy.cobook.domain.tag.TagRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.post.*;
import com.ssafy.cobook.service.dto.postbookmark.PostBookMarkReqDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsReqDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsResDto;
import com.ssafy.cobook.service.dto.postlike.PostLikeReqDto;
import com.ssafy.cobook.service.dto.posttag.PostTagDto;
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
public class PostService {

    private final PostRepository postRepository;
    private final ClubRepository clubRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostBookMarkRepository postBookMarkRepository;
    private final PostCommentRepository postCommentRepository;
    private final BookRepository bookRepository;

    @Transactional
    public PostSaveResDto savePosts(PostSaveReqDto reqDto, Long userId) {
        Post post = reqDto.toEntity();
        User user = getUserById(userId);
        post.of(user);
        Book book = getBook(reqDto.getBookId());
        post.ofBook(book);
        post = postRepository.save(post);
        user.addPosts(post);
        book.connetPost(post);
        List<Tag> tags = reqDto.getTags().stream()
                .map(this::saveTag)
                .collect(Collectors.toList());
        Post finalPost = post;
        List<PostTag> postTags = tags.stream()
                .map(t -> new PostTag(finalPost, t))
                .map(postTagRepository::save)
                .collect(Collectors.toList());
        post.setTags(postTags);
        return new PostSaveResDto(post);
    }

    @Transactional
    public Tag saveTag(String tagName) {
        if (tagRepository.findByTagName(tagName).isPresent()) {
            tagRepository.findByTagName(tagName);
        }
        return tagRepository.save(new Tag(tagName));
    }

    public List<PostResDto> getFollowPosts(Long userId) {
        User user = getUserById(userId);
        List<Follow> followings = followRepository.findAllByFromUser(user);
        List<PostResDto> posts = followings.stream()
                .filter(Follow::getIsClub)
                .map(f -> getPostByClub(f.getClub()))
                .map(PostResDto::ofClub)
                .collect(Collectors.toList());
        posts.addAll(followings.stream()
                .filter(f -> !f.getIsClub())
                .map(f -> getPostByUser(f.getToUser()))
                .map(PostResDto::ofUser)
                .collect(Collectors.toList()));
        return posts;
    }

    private Post getPostByUser(User toUser) {
        return postRepository.findByUser(toUser)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    private Post getPostByClub(Club club) {
        return postRepository.findByClub(club)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }

    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    public PostDetailResDto details(Long postId) {
        Post post = getPostById(postId);
        return new PostDetailResDto(post);
    }

    @Transactional
    public void likePosts(PostLikeReqDto reqDto, Long userId) {
        Post post = getPostById(reqDto.getPostId());
        User user = getUserById(userId);
        PostLike postLike = new PostLike(post, user);
        postLikeRepository.save(postLike);
    }

    @Transactional
    public void bookMarks(PostBookMarkReqDto reqDto, Long userId) {
        Post post = getPostById(reqDto.getPostId());
        User user = getUserById(userId);
        PostBookMark postBookMark = new PostBookMark(post, user);
        postBookMarkRepository.save(postBookMark);
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_POST));
    }

    public List<CommentsResDto> getComments(Long postId) {
        Post post = getPostById(postId);
        return postCommentRepository.findAllByPost(post).stream()
                .map(CommentsResDto::new)
                .collect(Collectors.toList());
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_BOOK));
    }

    public void addComments(CommentsReqDto dto) {
        User user = getUserById(dto.getUserId());
        Post post = getPostById(dto.getPostId());
        PostComment postComment = postCommentRepository.save(new PostComment(post, user, dto.getContents()));
        user.addComments(postComment);
        post.addComments(postComment);
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .filter(Post::getOpen)
                .map(PostResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<PostByClubResponseDto> getClubPosts(Long clubId) {
        return postRepository.findAll().stream()
                .filter(Post::getOpen)
                .filter(p->p.getClub().getId().equals(clubId))
                .map(PostByClubResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public PostSaveResDto saveClubPosts(PostSaveByClubReqDto requsetDto, Long clubId) {
        Post post = requsetDto.toEntity();
        Club club = getClub(clubId);
        post.of(club);
        Book book = getBook(requsetDto.getBookId());
        post.ofBook(book);
        post = postRepository.save(post);
        club.addPosts(post);
        book.connetPost(post);
        return new PostSaveResDto(post.getId());
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(()-> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }
}
