package com.ssafy.db.repository;

import com.ssafy.db.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 미팅룸 리스트 관련 디비 쿼리 생성을 위한 JPA Query Method 인터페이스 정의.
 */
@Repository
public interface ListRepository extends JpaRepository<Room, Long> {
    // 아래와 같이, Query Method 인터페이스(반환값, 메소드명, 인자) 정의를 하면 자동으로 Query Method 구현됨.
    Page<Room> findByEndTime(LocalDateTime endTime, Pageable pageable);
    Page<Room> findByTitleContainingAndEndTimeIsNull(String title, Pageable pageable);
    Page<Room> findByDescriptionContainingAndEndTimeIsNull(String description, Pageable pageable);
}
