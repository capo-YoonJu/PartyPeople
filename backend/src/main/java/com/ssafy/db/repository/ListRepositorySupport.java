package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoom;
import com.ssafy.db.entity.QUser;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 미팅룸 리스트, 태그 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class ListRepositorySupport {
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    QRoom qRoom = QRoom.room;
}
