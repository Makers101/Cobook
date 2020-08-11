package com.ssafy.cobook.domain.onedayevent;

import com.ssafy.cobook.service.dto.onedayevent.OneDayEventBySearchDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OneDayEventRepository extends JpaRepository<OneDayEvent, Long> {

    Optional<OneDayEvent> findByTitle(String title);
    @Query("SELECT o FROM OneDayEvent o where o.title like %:keyword% OR o.book.title like %:keyword%")
    List<OneDayEventBySearchDto> findByKeyword(@Param("keyword") String keyword);
}
