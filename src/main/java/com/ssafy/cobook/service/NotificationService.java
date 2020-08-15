package com.ssafy.cobook.service;

import com.google.firebase.database.*;
import com.ssafy.cobook.domain.club.Club;
import com.ssafy.cobook.domain.club.ClubRepository;
import com.ssafy.cobook.domain.clubmember.ClubMember;
import com.ssafy.cobook.domain.clubmember.ClubMemberRepository;
import com.ssafy.cobook.domain.clubmember.MemberRole;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.post.Post;
import com.ssafy.cobook.domain.post.PostRepository;
import com.ssafy.cobook.domain.postcomment.PostComment;
import com.ssafy.cobook.domain.postcomment.PostCommentRepository;
import com.ssafy.cobook.domain.postlike.PostLikeRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.notification.NotificationReqDto;
import com.ssafy.cobook.service.dto.notification.NotificationSaveDto;
import com.ssafy.cobook.service.dto.postcomment.CommentsResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final UserRepository userRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final FollowRepository followRepository;
    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;
    private final PostCommentRepository postCommentRepository;

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    private Post getPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_POST));
    }

    private Club getClub(Long clubId) {
        return clubRepository.findById(clubId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_CLUB));
    }

    private void saveNotificationData(Long fromUserId, DatabaseReference notiRef, String type) {
        notiRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                exFindData:
                for (DataSnapshot data : snapshot.getChildren()) {
                    String postKey = data.getKey();
                    for (DataSnapshot valueForFrom : data.getChildren()) {
                        if (valueForFrom.getKey().equals("from")) {
                            if (valueForFrom.getValue() == fromUserId) {
                                for (DataSnapshot valueForType : data.getChildren()) {
                                    if (valueForType.getKey().equals("type")) {
                                        if (valueForType.getValue().equals(type)) {
                                            DatabaseReference deleteRef = notiRef.child(postKey);
                                            deleteRef.removeValueAsync();
                                            break exFindData;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    @Transactional
    public void saveNoti(NotificationReqDto notificationReqDto, Long fromUserId) {
        LocalDateTime curDateTime = LocalDateTime.now();
        String nowDate = curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));


        // 저장할 데이터
        NotificationSaveDto notificationSaveDto = new NotificationSaveDto(nowDate, notificationReqDto.getDataId(), fromUserId, notificationReqDto.getIsRead(), notificationReqDto.getType());

        String type = notificationReqDto.getType();

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
        Long toId = 0L;


        Club club = null;
        Post post = null;

        if (type.equals("club")) {
            club = getClub(notificationReqDto.getDataId());

            List<ClubMember> clubMemberList = clubMemberRepository.findByClub(club); // 해당 클럽의 멤버들을 뽑아서
            for (ClubMember clubMember : clubMemberList) {
                if (clubMember.getRole().equals(MemberRole.LEADER)) {
                    toId = clubMember.getUser().getId();
                    break;
                }
            }
        } else if (type.equals("like") || type.equals("comment")) {
            post = getPost(notificationReqDto.getDataId());
            toId = notificationReqDto.getTo();
        } else {
            toId = notificationReqDto.getTo();
        }

        User fromUser = getUser(fromUserId);
        User toUser = getUser(toId);


        DatabaseReference notiRef = ref.child(toId.toString()); // 알림 받는 사람의 아이디
        DatabaseReference nextNotiRef = notiRef.push(); // 다음 키값으로 푸시
        String postId = nextNotiRef.getKey(); // 현재 알람의 키값을 가져옴
        DatabaseReference saveNoti = notiRef.child(postId); // to의 아이디 값의 child node


        if (type.equals("club")) { // 클럽 가입 신청
            if (clubMemberRepository.findByUserAndClub(fromUser, club).isPresent()) {
                saveNotificationData(fromUserId, notiRef, type);
            } else {
                saveNoti.setValueAsync(notificationSaveDto);
            }
        } else if (type.equals("follow")) { // 팔로우
            if (followRepository.findByToUserAndFromUser(fromUser.getId(), toUser.getId()).isPresent()) {
                saveNotificationData(fromUserId, notiRef, type);
            } else {
                saveNoti.setValueAsync(notificationSaveDto);
            }
        } else if (type.equals("like")) { // 좋아요
            if (postLikeRepository.findByUserAndPost(fromUser, post).isPresent()) {
                saveNotificationData(fromUserId, notiRef, type);
            } else {
                saveNoti.setValueAsync(notificationSaveDto);
            }
        } else { // 댓글
            PostComment postComment = postCommentRepository.findByLastComment(fromUser, post).stream()
                    .limit(1)
                    .collect(Collectors.toList()).get(0);
            notificationSaveDto.setCommentId(postComment.getId());
            saveNoti.setValueAsync(notificationSaveDto);
        }
    }


    @Transactional
    public void readNoti(String notiId, Long userId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
        DatabaseReference notiRef = ref.child(userId.toString()); // noti의 child node: to의 아이디 값
        DatabaseReference updateRef = notiRef.child(notiId);
        updateRef.child("isRead").setValueAsync(true);
    }

    @Transactional
    public void deleteNoti(String notiId, Long userId) {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
        DatabaseReference notiRef = ref.child(userId.toString()); // noti의 child node: to의 아이디 값
        DatabaseReference deleteRef = notiRef.child(notiId);
        deleteRef.removeValueAsync();
    }

    @Transactional
    public void deleteCommentAlert(Long commentId, Long userId) {
        if (postCommentRepository.findById(commentId).isPresent()) {
            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
            DatabaseReference notiRef = ref.child(userId.toString()); // noti의 child node: to의 아이디 값
            notiRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    exFindData:
                    for (DataSnapshot data : snapshot.getChildren()) {
                        String postKey = data.getKey();
                        for (DataSnapshot value : data.getChildren()) {
                            if (value.getKey().equals("commentId")) {
                                if (value.getValue() == commentId) {
                                    DatabaseReference deleteRef = notiRef.child(postKey);
                                    deleteRef.removeValueAsync();
                                    break exFindData;
                                }
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError error) {
                }
            });
        }
    }

}
