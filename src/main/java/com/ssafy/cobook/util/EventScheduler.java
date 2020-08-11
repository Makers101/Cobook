package com.ssafy.cobook.util;

import com.ssafy.cobook.service.ClubEventService;
import com.ssafy.cobook.service.OneDayEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventScheduler {

    private static final Logger logger = LoggerFactory.getLogger(EventScheduler.class);

    private final OneDayEventService oneDayEventService;
    private final ClubEventService clubEventService;

    @Scheduled(cron = "59 0/30 * * * *")
    public void cronJob() {
        logger.info("scheduled");
        oneDayEventService.checkEnded();
        clubEventService.checkEnded();
    }
}
