package com.ssafy.cobook.service;

import com.google.firebase.database.*;
import com.ssafy.cobook.domain.follow.FollowRepository;
import com.ssafy.cobook.domain.notification.Notification;
import com.ssafy.cobook.domain.notification.NotificationRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.exception.UserException;
import com.ssafy.cobook.service.dto.notification.NotificationReqDto;
import com.ssafy.cobook.service.dto.notification.NotificationSaveDto;
import com.ssafy.cobook.service.dto.notification.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public List<NotificationResponseDto> getNotis(Long userId) {
        User user = getUser(userId);
        return notificationRepository.findAllByToId(userId).stream()
                .map(NotificationResponseDto::new)
                .sorted()
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    @Transactional
    public void saveNoti(NotificationReqDto requestDto, Long fromUserId) {
        LocalDateTime curDateTime = LocalDateTime.now();
        String nowDate = curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss"));

        NotificationSaveDto notificationSaveDto = new NotificationSaveDto(nowDate, requestDto.getDataId(), fromUserId, requestDto.getIsRead(), requestDto.getType());


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti"); // 최상위 root: noti
        DatabaseReference notiRef = ref.child(requestDto.getTo().toString()); // noti의 child node: to의 아이디 값
        DatabaseReference nextNotiRef = notiRef.push(); // 다음 키값으로 푸시
        String postId = nextNotiRef.getKey(); // 현재 알람의 키값을 가져옴
        DatabaseReference saveNoti = notiRef.child(postId); // to의 아이디 값의 child node

//        String type = requestDto.getType();
//
//        if (type.equals("club")) { // 클럽 가입 신청
//
//        } else if (type.equals("clubApprove")) { // 클럽 가입 승인/거절
//
//        } else if (type.equals("follow")) { // 팔로우
//            User fromUser = getUser(fromUserId);
//            User toUser = getUser(requestDto.getTo());
//
//            if (!followRepository.findByToUser(fromUser, toUser).isPresent()) { // 이미 팔로우 되있는 경우면 noti에서 삭제
//
//            } else { //아닌 경우는 저장
//                saveNoti.setValueAsync(notificationSaveDto);
//            }
//        } else if (type.equals("like")) { // 좋아요
//
//        } else if (type.equals("comment")) { // 댓글
//
//        }

        saveNoti.setValueAsync(notificationSaveDto);

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

}
