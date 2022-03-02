package com.ssafy.db.repository;

import com.ssafy.db.entity.SuggestionRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 유저 모델 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface SuggestionRoomRepository extends JpaRepository<SuggestionRoom, Long> {

}