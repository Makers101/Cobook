package com.ssafy.cobook.service.dto.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationReqDto {
    private Long to;
    private Long dataId;
    private Boolean isRead;
    private String type;

}
