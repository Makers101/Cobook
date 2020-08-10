package com.ssafy.cobook.service.dto.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationReqDto {
    private Long to;
    private Long dataId; // 클럽 아이디
    private Boolean isRead;
    private String type;
}
