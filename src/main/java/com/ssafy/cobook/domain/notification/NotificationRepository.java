package com.ssafy.cobook.domain.notification;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    List<Notification> findAllByToId(Long to);
    Optional<Notification> findByFromIdAndToIdAndDataIdAndTypes(Long fromId, Long toId, Long dataId, String types);
}
