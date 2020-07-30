package com.ssafy.cobook.service;

import com.ssafy.cobook.domain.notification.Notification;
import com.ssafy.cobook.domain.notification.NotificationRepository;
import com.ssafy.cobook.domain.user.User;
import com.ssafy.cobook.domain.user.UserRepository;
import com.ssafy.cobook.exception.BaseException;
import com.ssafy.cobook.exception.ErrorCode;
import com.ssafy.cobook.service.dto.notification.NotificationRequestDto;
import com.ssafy.cobook.service.dto.notification.NotificationResponseDto;
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
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    public List<NotificationResponseDto> getNotis(Long userId) {
        User user = getUser(userId);
        return notificationRepository.findAllByToId(userId).stream()
                .filter(Notification::unread)
                .map(NotificationResponseDto::new)
                .sorted()
                .collect(Collectors.toList());
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new BaseException(ErrorCode.UNEXPECTED_USER));
    }

    @Transactional
    public void saveNoti(NotificationRequestDto requestDto) {
        notificationRepository.save(requestDto.toEntity());
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
