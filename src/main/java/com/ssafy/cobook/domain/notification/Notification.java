package com.ssafy.cobook.domain.notification;

import com.ssafy.cobook.domain.baseEntity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long from;
    private Long to;
    private Long dataId;
    private String type;
    private Boolean read;

    @Builder
    public Notification(Long from, Long to, Long dataId, String type) {
        this.from = from;
        this.to = to;
        this.dataId = dataId;
        this.type = type;
        this.read = false;
    }

    public boolean unread() {
        return !read;
    }

    public void read() {
        this.read = true;
    }
}