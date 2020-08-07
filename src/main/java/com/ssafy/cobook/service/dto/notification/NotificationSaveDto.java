package com.ssafy.cobook.service.dto.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class NotificationSaveDto {
    private String createdAt;
    private Long clubId;
    private Long from;
    private Boolean isRead;
    private String type;


    public NotificationSaveDto(String createdAt, Long clubId, Long from, Boolean isRead, String type){
        this.createdAt = createdAt;
        this.clubId = clubId;
        this.from = from;
        this.isRead = isRead;
        this.type = type;
    }
}
