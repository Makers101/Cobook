package com.ssafy.cobook.service.dto.club;

import com.ssafy.cobook.domain.club.Club;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@NoArgsConstructor
public class ClubCreateReqDto {

    private Long userId;
    private String name;
    private String oneline_description;
    private String description;
    private String residence;
    private List<String> clubGenres;
    private MultipartFile image;

    public Club toEntity() {
        return Club.builder()
                .description(description)
                .onelineDescription(oneline_description)
                .name(name)
                .residence(residence)
                .build();
    }
}
