package com.ssafy.cobook.service.dto.tag;

import com.ssafy.cobook.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TagResponseDto {

    private Long id;
    private String tagName;

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();
        this.tagName = tag.getTagName();
    }
}
