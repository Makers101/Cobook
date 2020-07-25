package com.ssafy.cobook.service.dto.tag;

import com.ssafy.cobook.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagByPostDto {

    private Long id;
    private String name;

    public TagByPostDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getTagName();
    }
}
