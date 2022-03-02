package com.ssafy.api.service;

import com.ssafy.db.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 *	파티룸 리스트, 태그 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface ListService {
    Page<Room> getRoomList(Pageable pageable);
    Page<Room> getRoomListByWord(String word, String include, Pageable pageable);
    List<String> getRelativeKeyward(String include, String word);
}
