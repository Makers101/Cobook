package com.ssafy.cobook.service.dto.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ProfileByStatisticsForPeriod {
    private String period;
    private Long count;
    public ProfileByStatisticsForPeriod(String period, Long count){
        this.period = period;
        this.count = count;
    }
}
