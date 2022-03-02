package com.ssafy.db.repository;

import com.ssafy.db.entity.RoomTag;
import com.ssafy.db.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 태그와 룸 테이블 조인 테이블인 룸 태그 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface RoomTagRepository extends JpaRepository<RoomTag, Long> {
    List<RoomTag> findByTag(Tag tag);
}
