package com.ssafy.cobook.service.dto.notification;

import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NotificationReqDto {
    private Long toUserId;
    private Long dataId;
    private Boolean isRead;
    private String type;

}
