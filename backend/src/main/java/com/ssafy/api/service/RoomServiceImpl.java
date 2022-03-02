package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.request.RoomHostUpdateReq;
import com.ssafy.db.entity.*;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service("roomService")
public class RoomServiceImpl implements RoomService {
    @Autowired
    RoomRepository roomRepository;
    @Autowired
    RoomRepositorySupport roomRepositorySupport;
    @Autowired
    SessionRepository sessionRepository;
    @Autowired
    SessionRepositorySupport sessionRepositorySupport;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TagRepository tagRepository;
    @Autowired
    RoomTagRepository roomTagRepository;
    @Autowired
    FileUploadService fileUploadService;
    @Autowired
    SuggestionRoomRepository suggestionRoomRepository;
    @Autowired
    SuggestionTagRepository suggestionTagRepository;

    @Override
    public Room createRoom(RoomCreatePostReq req, MultipartFile multipartFile) {
        Room room = new Room();

        room.setTitle(req.getTitle());
        room.setDescription(req.getDescription());
        room.setCapacity(req.getCapacity());
        if (!req.getPassword().isEmpty()) {
            room.setPassword(req.getPassword());
            room.setLocked(true);
        }
        else
            room.setLocked(false);

        LocalDateTime curDateTime = LocalDateTime.now();
        curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        room.setStartTime(curDateTime);

        room.setThumbnailUrl("dummy.png");    // 임시 썸네일 경로
        room = roomRepository.save(room);       // 썸네일 경로 없이 방 생성
        sessionRepository.flush();

        // tag를 새롭게 저장하고 room 객체에 매핑
        room.setRoomTags(saveAndGetRoomTags(room, req.getHashtag()));

        suggestionRoomRepository.save(new SuggestionRoom(room));    // 캐시테이블에 저장

        return this.updateThumbnail(room.getId(), multipartFile);   // 썸네일 경로 업데이트
    }

    // tag 테이블에 태그를 저장하고 room_tag 테이블에 반영한다
    @Override
    public List<RoomTag> saveAndGetRoomTags(Room room, String tagString) {
        if (tagString.length() == 0 || tagString == null) return null;
        String[] hashtags = tagString.substring(1).split("#");
        List<RoomTag> roomTagList = updateTags(room, hashtags);
        return roomTagRepository.saveAll(roomTagList);
    }

    @Override
    public List<RoomTag> updateTags(Room room, String[] hashtags) {
        List<RoomTag> roomTagList = new ArrayList<>();

        for (String hashtag : hashtags) {
            Tag tag = tagRepository.findByTagName(hashtag);
            if (tag == null) {
                Tag newTag = new Tag();
                newTag.setTagName(hashtag);
                roomTagList.add(new RoomTag(room, tagRepository.save(newTag)));
                suggestionTagRepository.save(new SuggestionTag(newTag));   // 캐시테이블에 저장
            }
            else roomTagList.add(new RoomTag(room, tag));
        }
        return roomTagList;
    }

    @Override
    public List<User> getRoomUserListByRoomId(Long roomId) {
        return sessionRepositorySupport.findUsersByRoomId(roomId);
    }

    @Override
    public List<Session> getSessionsByRoomId(Long roomId) {
        return sessionRepositorySupport.findSessionByRoomId(roomId);
    }

    @Override
    public void updateRoomHostInfo(Long roomId, List<RoomHostUpdateReq> req) {
        for (RoomHostUpdateReq host : req) {
            Session updatedSession = sessionRepositorySupport.findSessionByRoomIdAndUserId(roomId, host.getId());
            if (host.getAction() == 0) updatedSession.setHost(false);
            else updatedSession.setHost(true);
            sessionRepository.save(updatedSession);
        }
    }

    @Override
    public void updateSessionEndTime(Long roomId, Long userId) {
        Session updatedSession = sessionRepositorySupport.findSessionByRoomIdAndUserId(roomId, userId);
        LocalDateTime curDateTime = LocalDateTime.now();
        curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        updatedSession.setEndTime(curDateTime);
        sessionRepository.save(updatedSession);
    }

    @Override
    public void closeAllUserSession(Long roomId) {
        List<User> userList = getRoomUserListByRoomId(roomId);
        for (User user : userList) {
            updateSessionEndTime(roomId, user.getId());
        }
    }

    @Override
    public boolean checkRoomUserExist(Long roomId) {
        List<Session> sessions = getSessionsByRoomId(roomId);
        if(sessions.isEmpty()) return false;
        return true;
    }

    @Override
    public boolean isNotSessionExist(Long roomId) {
        List<Session> list = sessionRepository.findByRoomId(roomId);

        if (list.isEmpty()) return true;
        return false;
    }

    @Override
    public Room findByRoomId(Long roomId) {
        return roomRepository.findById(roomId).get();
    }

    @Override
    public boolean roomEntry(User user, Long roomId, String password) {
        Room room = roomRepository.findById(roomId).get();
        // TODO: capacity 확인
        if (room.getPassword() == null) {
            Session session = new Session();
            session.setUser(user);
            session.setRoom(room);
            sessionRepository.save(session);
            return true;
        }
        if (room.getPassword().equals(password)) {
            Session session = new Session();
            session.setUser(user);
            session.setRoom(room);
            sessionRepository.save(session);
            return true;
        }

        return false;
    }

    @Override
    public Room deleteRoom(long roomId) {
        // 파티룸 삭제
        //TODO: findbyid를 가장 최근 값 하나만 찾아내기
        Room room = roomRepository.findById(roomId).get();
        LocalDateTime curDateTime = LocalDateTime.now();
        curDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        room.setEndTime(curDateTime);
        return roomRepository.save(room);
    }

    @Override
    public boolean isSessionClosed(Long roomId) {
        Room room = roomRepositorySupport.getActiveRoomByRoomId(roomId);
        return room != null;
    }

    @Override
    public boolean isNotQualifiedHost(Long roomId, Long userId) {
        Session session = sessionRepositorySupport.getSessionByRoomIdAndHostId(roomId, userId);
        return session == null;
    }

    @Override
    // 이미 세션에 접속한 사용자가 다른 세션에 접근할 때 예외처리
    public boolean isUserAccessOtherSession(Long userId) {
        return sessionRepositorySupport.isUserAccessOtherSession(userId);
    }

    @Override
    // 해당 방에 접속해있는 사용자인지 확인
    public boolean isUserInCurrentSession(Long roomId, Long userId) {
        return sessionRepositorySupport.isUserInCurrentSession(roomId, userId);
    }

    @Override
    public boolean isSelectedHostIsNone(List<RoomHostUpdateReq> req) {
        for (RoomHostUpdateReq request : req) {
            if (request.getAction() == 1) return false;
        }
        return true;
    }

    @Override
    // 사용자가 방에 입장
    public void createSession(Long roomId, Long userId, boolean isHost) {
        Room room = roomRepository.findById(roomId).get();
        User user = userRepository.findByAccountId(Long.toString(userId));
        Session session = new Session();

        if(room == null || user == null)
            return;

        session.setRoom(room);
        session.setUser(user);
        session.setHost(isHost);

        sessionRepository.save(session);
    }

    @Override
    // 썸네일 경로 업데이트
    public Room updateThumbnail(long roomId, MultipartFile multipartFile) {
        Room room = this.findByRoomId(roomId);  // 썸네일 경로가 없는 룸 얻어옴
        String thumbnailPath = fileUploadService.saveFile(multipartFile, roomId);   // 썸네일 경로 생성

        room.setThumbnailUrl(thumbnailPath);    // 썸네일 경로 설정
        return roomRepository.save(room);   // 섬네일 경로를 업데이트
    }
}
