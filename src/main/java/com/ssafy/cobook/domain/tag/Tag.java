package com.ssafy.cobook.domain.tag;

import com.ssafy.cobook.domain.posttag.PostTag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private Long id;

    private String tagName;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<PostTag> postTags = new ArrayList<>();

    public Tag(String tagName) {
        this.tagName = tagName;
    }

    public void removePostTag(PostTag tag) {
        this.postTags.remove(tag);
    }
}
