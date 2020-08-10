package com.ssafy.cobook.service.dto.profile;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class ProfileStatisticsResDto {
    private List<ProfileByStatisticsForGenre> genreByPostData;
    private List<ProfileByStatisticsForPeriod> monthByPostData;

    public ProfileStatisticsResDto(List<ProfileByStatisticsForGenre> genreByPostData, List<ProfileByStatisticsForPeriod> monthByPostData) {
        this.genreByPostData = genreByPostData;
        this.monthByPostData = monthByPostData;
    }
}
