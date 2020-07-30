package com.ssafy.cobook.service.dto.notification;

import com.ssafy.cobook.domain.notification.Notification;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NotificationResponseDto implements Comparable<NotificationResponseDto> {

    private Long id;
    private Long from;
    private Long to;
    private Long dataId;
    private String type;
    private boolean isread;
    private LocalDateTime createdAt;

    public NotificationResponseDto(Notification noti) {
        this.id = noti.getId();
        this.from = noti.getFromId();
        this.to = noti.getToId();
        this.dataId = noti.getDataId();
        this.type = noti.getTypes();
        this.isread = noti.getIsread();
        this.createdAt = noti.getCreatDateTime();
    }

    @Override
    public int compareTo(NotificationResponseDto o) {
        return -this.createdAt.compareTo(o.createdAt);
    }
}
