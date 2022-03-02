package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomEntryPostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.db.entity.Room;
import com.ssafy.db.entity.RoomTag;
import com.ssafy.db.entity.Session;
import com.ssafy.db.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 *	유저 관련 비즈니스 로직 처리를 위한 서비스 인터페이스 정의.
 */
public interface RoomService {
	Room createRoom(RoomCreatePostReq roomCreatePostReq, MultipartFile multipartFile);
	Room deleteRoom(long roomId);
	Room updateThumbnail(long roomId, MultipartFile multipartFile);
	// tag 테이블에 태그를 저장하고 room_tag 테이블에 반영한다
	List<RoomTag> saveAndGetRoomTags(Room room, String tagString);
	List<RoomTag> updateTags(Room room, String[] hashtags);
	List<User> getRoomUserListByRoomId(Long roomId);
	List<Session> getSessionsByRoomId(Long roomId);
	void updateRoomHostInfo(Long roomId, List<RoomHostUpdateReq> updateHostReq);
	void updateSessionEndTime(Long roomId, Long userId);
	void closeAllUserSession(Long roomId);
	void createSession(Long roomId, Long userId, boolean isHost);
	Room findByRoomId(Long roomId);
	boolean roomEntry(User user, Long roomId, String password);
	boolean checkRoomUserExist(Long roomId);
	boolean isNotSessionExist(Long roomId);
	boolean isSessionClosed(Long roomId);
	boolean isNotQualifiedHost(Long roomId, Long userId);
	boolean isUserAccessOtherSession(Long userId);
	boolean isUserInCurrentSession(Long roomId, Long userId);
	boolean isSelectedHostIsNone(List<RoomHostUpdateReq> req);
}
