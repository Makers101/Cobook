package com.ssafy.cobook.service;

import com.google.firebase.database.*;
import com.ssafy.cobook.domain.notification.Notification;
import com.ssafy.cobook.domain.notification.NotificationRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.notification.NotificationReqDto;
import com.ssafy.cobook.service.dto.notification.NotificationSaveDto;
import com.ssafy.cobook.service.dto.notification.NotificationResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

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
    public void saveNoti(NotificationReqDto requestDto, Long userId) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        String nowDate = simpleDateFormat.format(now);

        NotificationSaveDto notificationSaveDto = new NotificationSaveDto(nowDate, requestDto.getClubId(), userId, requestDto.getIsRead(), requestDto.getType());

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("noti");
        DatabaseReference notiRef = ref.child(requestDto.getTo().toString()); // noti의 child인 to번호값
        DatabaseReference curNotiRef = notiRef.push(); // 알람의 현재 값
        String postId = curNotiRef.getKey(); // 현재 알람의 키값을 가져옴
        DatabaseReference saveNoti = notiRef.child(postId); // to의 child를 가져옴

        String type = requestDto.getType();

        if (type.equals("follow")) { /
            // 팔로우인 경우 알람을 저장한다
            // 언팔로우인 경우 이전에 팔로우로 등록한 알람을 삭제한다
            saveNoti.setValueAsync(notificationSaveDto);
        } else if(type.equals("clubApply")){
            // 클럽 가입 신청을 하면 클럽장한테 알림이 간다고 보면 됨 (이건 그냥 저장하면됨)
            // 근데 가입 신청을 취소하면 알림이 삭제되어야 하므로 또 데이터베이스 조회 필요

        }else if(type.equals("clubJoin")) {
            // 클럽 가입이 승인되는지 안되는지? 여부 있어야 할것 같음
            // 승인 여부로 메시지 다르게 하기
            saveNoti.setValueAsync(notificationSaveDto);
        }
    }

    @Transactional
    public void readNoti(Long notiId, Long userId) {
        Notification notification = notificationRepository.findById(notiId)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_NOTIFICATION));
        if (!notification.getToId().equals(userId)) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_NOTIFICATION);
        }
        if (notification.unread()) {
            notification.read();
        } else {
            throw new BaseException(ErrorCode.ALREADY_READ);
        }
    }

    @Transactional
    public void deleteNoti(Long notiId, Long userId) {
        Notification notification = notificationRepository.findById(notiId)
                .orElseThrow(() -> new BaseException(ErrorCode.ILLEGAL_ACCESS_NOTIFICATION));
        if (!notification.getToId().equals(userId)) {
            throw new BaseException(ErrorCode.ILLEGAL_ACCESS_NOTIFICATION);
        }
        notificationRepository.delete(notification);
    }
}
