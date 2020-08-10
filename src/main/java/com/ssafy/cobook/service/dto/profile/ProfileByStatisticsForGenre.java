package com.ssafy.cobook.service.dto.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProfileByStatisticsForGenre {
    private String name;
    private Long count;

    public ProfileByStatisticsForGenre(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
