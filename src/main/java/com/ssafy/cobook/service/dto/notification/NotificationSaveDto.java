package com.ssafy.cobook.service.dto.notification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class NotificationSaveDto {
    private Long from;
    private String createdAt;
    private Long dataId;
    private Boolean isRead;
    private String type;


    public NotificationSaveDto(String createdAt, Long dataId, Long from, Boolean isRead, String type){
        this.createdAt = createdAt;
        this.dataId = dataId;
        this.from = from;
        this.isRead = isRead;
        this.type = type;
    }

    public void setDataId(Long dataId){
        this.dataId = dataId;
    }
}
