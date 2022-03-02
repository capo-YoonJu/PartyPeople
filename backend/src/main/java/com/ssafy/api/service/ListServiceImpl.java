package com.ssafy.api.service;

import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.Session;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *	미팅룸 리스트, 태그 관련 비즈니스 로직 처리를 위한 서비스 구현 정의.
 */
@Service("listService")
public class ListServiceImpl implements ListService {
	@Autowired
	private ListRepository listRepository;
	@Autowired
	private RoomTagRepositorySupport roomTagRepositorySupport;
	@Autowired
	private SuggestionRoomRepositorySupport suggestionRoomRepositorySupport;
	@Autowired
	private SuggestionTagRepositorySupport suggestionTagRepositorySupport;

	@Override
	public Page<Room> getRoomList(Pageable pageable) {
		Pageable sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
		return listRepository.findByEndTime(null, sort);
	}

	@Override
	public Page<Room> getRoomListByWord(String word, String include, Pageable pageable) {
		Pageable sort = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
		Page<Room> roomList = null;

		switch (include) {
			case "title" :
				roomList = listRepository.findByTitleContainingAndEndTimeIsNull(word, sort);
				break;
			case "des" :
				roomList = listRepository.findByDescriptionContainingAndEndTimeIsNull(word, sort);
				break;
			case "hashtag" :
				String[] hashtags = word.substring(1).split("#");
				roomList = roomTagRepositorySupport.getRoomTagByTagName(hashtags, sort);
				break;
		}

		deleteCloseSession(roomList); // 얕은 복사
		return roomList;
	}

	// roomList 안에 있는 session 중에서 endTime 값이 있는(접속을 종료한) 것을 삭제하는 코드
	// TODO: JPA 단에서 개선이 가능하다면 개선 시키기
	void deleteCloseSession(Page<Room> roomList) {
		for(Room room : roomList) {
			List<Session> sessionList = room.getSessions();

			Iterator<Session> iter = sessionList.iterator();
			while(iter.hasNext()) {
				Session session = iter.next();
				if(session.getEndTime() != null) iter.remove();
			}
		}
	}

	@Override
	public List<String> getRelativeKeyward(String include, String word) {
		List<String> suggestion = null;

		switch (include) {
			case "title" :
				suggestion = suggestionRoomRepositorySupport.getAppropriateTitle(word);
				break;
			case "des" :
				suggestion = suggestionRoomRepositorySupport.getAppropriateDescription(word);
				break;
			case "hashtag" :
				suggestion = new ArrayList<>();
				for(String token : word.split("#")){
					if(token.isBlank())
						continue;
					suggestion.addAll(suggestionTagRepositorySupport.getAppropriateTag(token));
				}
				break;
		}

		return suggestion;
	}
}
