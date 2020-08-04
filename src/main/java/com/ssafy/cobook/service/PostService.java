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
import com.ssafy.cobook.service.dto.postcomment.CommentsReqDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsResDto;
import com.ssafy.cobook.service.dto.tag.TagResponseDto;
import com.ssafy.cobook.util.PageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
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
        post.setUsers();
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
                .map(t -> saveTag(finalPost, t))
                .collect(Collectors.toList());
        post.setTags(postTags);
        return new PostSaveResDto(post);
    }

    @Transactional
    public Tag saveTag(String tagName) {
        if (tagRepository.findByTagName(tagName).isPresent()) {
            return tagRepository.findByTagName(tagName).get();
        }
        return tagRepository.save(new Tag(tagName));
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
    public void likePosts(Long postId, Long userId) {
        Post post = getPostById(postId);
        User user = getUserById(userId);
        if (postLikeRepository.findByUserAndPost(user, post).isPresent()) {
            PostLike postLike = postLikeRepository.findByUserAndPost(user, post).get();
            post.deleteLike(postLike);
            user.deleteLike(postLike);
            postLikeRepository.delete(postLike);
        } else {
            PostLike postLike = postLikeRepository.save(new PostLike(post, user));
            user.addLikes(postLike);
            post.addLikes(postLike);
        }
    }

    @Transactional
    public void bookMarks(Long postId, Long userId) {
        Post post = getPostById(postId);
        User user = getUserById(userId);
        if (postBookMarkRepository.findByUserAndPost(user, post).isPresent()) {
            PostBookMark postBookMark = postBookMarkRepository.findByUserAndPost(user, post).get();
            post.deleteBookMark(postBookMark);
            user.deleteBookMark(postBookMark);
            postBookMarkRepository.delete(postBookMark);
        } else {
            PostBookMark postBookMark = postBookMarkRepository.save(new PostBookMark(post, user));
            user.addBookMarks(postBookMark);
            post.addBookMarks(postBookMark);
        }
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_POST));
    }

    public List<CommentsResDto> getComments(Long postId) {
        Post post = getPostById(postId);
        return postCommentRepository.findAllByPost(post).stream()
                .map(CommentsResDto::new)
                .sorted()
                .collect(Collectors.toList());
    }

    private Book getBook(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_BOOK));
    }

    public void addComments(Long userId, Long postId, CommentsReqDto dto) {
        User user = getUserById(userId);
        Post post = getPostById(postId);
        PostComment postComment = postCommentRepository.save(new PostComment(post, user, dto.getContent()));
        user.addComments(postComment);
        post.addComments(postComment);
    }

    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
                .filter(Post::getOpen)
                .map(PostResponseDto::new)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<PostByClubResponseDto> getClubPosts(Long clubId) {
        return postRepository.findAll().stream()
                .filter(Post::getOpen)
                .filter(p -> p.getClub().getId().equals(clubId))
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
        List<Tag> tags = requsetDto.getTags().stream()
                .map(this::saveTag)
                .collect(Collectors.toList());
        Post finalPost = post;
        List<PostTag> postTags = tags.stream()
                .map(t -> saveTag(finalPost, t))
                .collect(Collectors.toList());
        post.setTags(postTags);
        return new PostSaveResDto(post.getId());
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }

    public List<TagResponseDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(TagResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updatePost(Long postId, Long userId, PostUpdateReqDto requestDto) {
        User user = getUserById(userId);
        Post post = getPostById(postId);
        if (post.getUser() == null || !post.getUser().equals(user)) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_POST);
        }
        List<PostTag> originTags = post.getTags();
        List<Tag> tags = requestDto.getTags().stream()
                .map(this::saveTag)
                .collect(Collectors.toList());
        for (PostTag tag : originTags) {
            Tag temp = tag.getTag();
            if (!tags.contains(temp)) {
                temp.removePostTag(tag);
                post.deleteTags(tag);
                postTagRepository.delete(tag);
            }
        }
        List<PostTag> postTags = tags.stream()
                .map(t -> saveTag(post, t))
                .collect(Collectors.toList());
        post.updatePost(requestDto);
        post.addTags(postTags);
    }

    private PostTag saveTag(Post post, Tag tag) {
        if (postTagRepository.findByPostAndTag(post, tag).isPresent()) {
            return postTagRepository.findByPostAndTag(post, tag).get();
        }
        return postTagRepository.save(new PostTag(post, tag));
    }

    @Transactional
    public void updateClubPosts(PostUpdateByClubReqDto requestDto, Long clubId, Long postId) {
        Post post = getPostById(postId);
        Club club = getClub(clubId);
        List<PostTag> originTags = post.getTags();
        List<Tag> tags = requestDto.getTags().stream()
                .map(this::saveTag)
                .collect(Collectors.toList());
        List<PostTag> deletes = new ArrayList<>();
        for (PostTag tag : originTags) {
            Tag temp = tag.getTag();
            if (!tags.contains(temp)) {
                temp.removePostTag(tag);
                post.deleteTags(tag);
                deletes.add(tag);
            } else {
                tags.remove(temp);
            }
        }
        postTagRepository.deleteAll(deletes);
        List<PostTag> postTags = tags.stream()
                .map(t -> saveTag(post, t))
                .collect(Collectors.toList());
        post.updatePost(requestDto);
        post.setTags(postTags);
    }

    @Transactional
    public void deleteComment(Long userId, Long postId, Long commentId) {
        User user = getUserById(userId);
        Post post = getPostById(postId);
        PostComment postComment = postCommentRepository.findById(commentId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_COMMENTS));
        if (!postComment.getUser().equals(user) || !user.getNickName().equals("코북이")) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_COMMENT);
        }
        user.removeComment(postComment);
        post.removeComment(postComment);
        postCommentRepository.delete(postComment);
    }

    @Transactional
    public void deletePosts(Long userId, Long postId) {
        User user = getUserById(userId);
        Post post = getPostById(postId);
        if (!post.getUser().equals(user)) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_POST);
        }
        if (!user.getNickName().equals("코북이")) {
            List<PostLike> postLikes = postLikeRepository.findAllByPost(post);
            for (PostLike postLike : postLikes) {
                postLike.removeUser();
            }
            postLikeRepository.deleteAll(postLikes);
            List<PostTag> postTags = postTagRepository.findAllByPost(post);
            for (PostTag postTag : postTags) {
                postTag.removeTag();
            }
            postTagRepository.deleteAll(postTags);
            List<PostBookMark> bookMarks = postBookMarkRepository.findAllByPost(post);
            for (PostBookMark postBookMark : bookMarks) {
                postBookMark.removeUser();
            }
            postBookMarkRepository.deleteAll(bookMarks);
            List<PostComment> comments = postCommentRepository.findAllByPost(post);
            for (PostComment comment : comments) {
                comment.removeUser();
            }
            postCommentRepository.deleteAll(comments);
            user.removePost(post);
            postRepository.delete(post);
            return;
        }
        List<PostLike> postLikes = postLikeRepository.findAllByPost(post);
        for (PostLike postLike : postLikes) {
            postLike.removeUser();
        }
        postLikeRepository.deleteAll(postLikes);
        List<PostTag> postTags = postTagRepository.findAllByPost(post);
        for (PostTag postTag : postTags) {
            postTag.removeTag();
        }
        postTagRepository.deleteAll(postTags);
        List<PostBookMark> bookMarks = postBookMarkRepository.findAllByPost(post);
        for (PostBookMark postBookMark : bookMarks) {
            postBookMark.removeUser();
        }
        postBookMarkRepository.deleteAll(bookMarks);
        List<PostComment> comments = postCommentRepository.findAllByPost(post);
        for (PostComment comment : comments) {
            comment.removeUser();
        }
        postCommentRepository.deleteAll(comments);
        user.removePost(post);
        postRepository.delete(post);
    }

    public Page<PostResponseDto> getAllPostsPage(PageRequest pageRequest) {
        return postRepository.findAll(pageRequest.of()).map(PostResponseDto::new);
    }
}
