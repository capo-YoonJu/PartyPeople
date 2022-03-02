package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QRoom;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * 세션 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class RoomRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    private QRoom qRoom = QRoom.room;

    public Room getActiveRoomByRoomId(Long roomId) {
        Room room = jpaQueryFactory.select(qRoom).from(qRoom)
                .where(qRoom.id.eq(roomId).and(qRoom.endTime.isNotNull())).fetchOne();
        return room;
    }
}
