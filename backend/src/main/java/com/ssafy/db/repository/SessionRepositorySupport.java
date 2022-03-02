package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QSession;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 세션 모델 관련 디비 쿼리 생성을 위한 구현 정의.
 */
@Repository
public class SessionRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QSession qSession = QSession.session;

    public List<User> findUsersByRoomId(Long roomId) {
        List<User> users = jpaQueryFactory.select(qSession.user).from(qSession)
                .where(qSession.room.id.eq(roomId).and(qSession.endTime.isNull())).fetch();
        return users;
    }

    public List<Session> findSessionByRoomId(Long roomId) {
        List<Session> sessions = jpaQueryFactory.select(qSession).from(qSession)
                .where(qSession.room.id.eq(roomId).and(qSession.endTime.isNull())).fetch();
        return sessions;
    }

    public Session findSessionByRoomIdAndUserId(Long roomId, Long userId) {
        Session session = jpaQueryFactory.select(qSession).from(qSession)
                .where(qSession.room.id.eq(roomId).and(qSession.user.id.eq(userId)).and(qSession.endTime.isNull())).fetchFirst();
        return session;
    }

    // 이미 세션에 접속한 사용자가 다른 세션에 접근할 때 예외처리
    public boolean isUserAccessOtherSession(Long userId){
        Session session = jpaQueryFactory.select(qSession).from(qSession)
                .where(qSession.user.id.eq(userId).and(qSession.endTime.isNull())).fetchFirst();

        return session != null;
    }

    // 해당 방에 접속해있는 사용자인지 확인
    public boolean isUserInCurrentSession(Long roomId, Long userId){
        Session session = jpaQueryFactory.select(qSession).from(qSession)
                .where(qSession.room.id.eq(roomId).and(qSession.user.id.eq(userId)).and(qSession.endTime.isNull())).fetchFirst();

        return session != null;
    }

    // 유저 아이디로 해당 방 호스트인지 확인
    public Session getSessionByRoomIdAndHostId(Long roomId, Long userId) {
        Session session = jpaQueryFactory.select(qSession).from(qSession)
                .where(qSession.room.id.eq(roomId).and(qSession.user.id.eq(userId)).and(qSession.isHost.isTrue())).fetchFirst();
        return session;
    }
}
