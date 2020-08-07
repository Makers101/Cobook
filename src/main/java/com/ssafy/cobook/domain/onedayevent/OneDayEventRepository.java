package com.ssafy.cobook.domain.onedayevent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OneDayEventRepository extends JpaRepository<OneDayEvent, Long> {

    Optional<OneDayEvent> findByTitle(String title);
}
