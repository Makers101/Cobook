package com.ssafy.cobook.service.dto.posttag;

import com.ssafy.cobook.domain.tag.Tag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PostTagDto {

    private Long tagId;
    private String tagName;

    public PostTagDto(Tag tag) {
        this.tagId = tag.getId();
        this.tagName = tag.getTagName();
    }
}