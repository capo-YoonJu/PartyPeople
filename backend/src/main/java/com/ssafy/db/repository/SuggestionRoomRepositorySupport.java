package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoom;
import com.ssafy.db.entity.QSuggestionRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 방 제목, 설명 검색을 위한 구현 정의.
 */
@Repository
public class SuggestionRoomRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    private QSuggestionRoom qSuggestionRoom = QSuggestionRoom.suggestionRoom;

    public List<String> getAppropriateTitle(String word) {
        List<String> suggestionRoom = jpaQueryFactory.select(qSuggestionRoom.title).distinct().from(qSuggestionRoom).
                where(qSuggestionRoom.title.like(word + "%")).limit(10).fetch();

        return suggestionRoom;
    }

    public List<String> getAppropriateDescription(String word) {
        List<String> suggestionRoom = jpaQueryFactory.select(qSuggestionRoom.description).distinct().from(qSuggestionRoom).
                where(qSuggestionRoom.description.like(word + "%")).limit(10).fetch();

        return suggestionRoom;
    }
}
