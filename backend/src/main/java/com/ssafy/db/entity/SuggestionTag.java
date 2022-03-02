package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 태그 모델 정의.
 */
@Entity
@Table(name = "tag_in_memory")
@Getter
@Setter
public class SuggestionTag extends BaseEntity{
    private String tagName;

    public SuggestionTag(Tag tag) {
        this.tagName = tag.getTagName();
    }

    public SuggestionTag() {

    }
}
