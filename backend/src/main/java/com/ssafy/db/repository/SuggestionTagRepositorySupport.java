package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QSuggestionTag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 방 제목, 설명 검색을 위한 구현 정의.
 */
@Repository
public class SuggestionTagRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    private QSuggestionTag qSuggestionTag = QSuggestionTag.suggestionTag;

    public List<String> getAppropriateTag(String word) {
        List<String> suggestionTag = jpaQueryFactory.select(qSuggestionTag.tagName).distinct().from(qSuggestionTag).
                where(qSuggestionTag.tagName.like(word + "%")).limit(10).fetch();

        return suggestionTag;
    }
}
