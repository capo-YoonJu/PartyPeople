package com.ssafy.api.service;

import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.db.entity.Room;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/*
 * * * * * * * *
 * 방 생성 테스트 *
 * * * * * * * *
 * */

@SpringBootTest
@Transactional
class RoomServiceImplTest {
    @Autowired
    private RoomServiceImpl roomService;

    @Test
    void createRoom() {
        /** 테스트 1, 비밀번호 없는 방 생성 **/
        RoomCreatePostReq roomCreatePostReq1 = new RoomCreatePostReq();

        roomCreatePostReq1.setTitle("springtesttitle");
        roomCreatePostReq1.setDescription("springdesc");
        roomCreatePostReq1.setPassword("");
        roomCreatePostReq1.setCapacity("1");
        roomCreatePostReq1.setHashtag("#tag123#tag456");

        Room result1 = roomService.createRoom(roomCreatePostReq1, null);

        assertEquals("springtesttitle", result1.getTitle());
        assertEquals("springdesc", result1.getDescription());
        assertNull(result1.getPassword());
        assertFalse(result1.isLocked());
        assertEquals("1", result1.getCapacity());
        /** 테스트 1, 비밀번호 없는 방 생성 종료**/

        /** 테스트 2, 비밀번호 있는 방 생성 **/
        RoomCreatePostReq roomCreatePostReq2 = new RoomCreatePostReq();

        roomCreatePostReq2.setTitle("springtesttitle2");
        roomCreatePostReq2.setDescription("springdesc2");
        roomCreatePostReq2.setPassword("1234");
        roomCreatePostReq2.setCapacity("2");
        roomCreatePostReq2.setHashtag("#tag123#tag456");

        Room result2 = roomService.createRoom(roomCreatePostReq2, null);

        assertEquals("springtesttitle2", result2.getTitle());
        assertEquals("springdesc2", result2.getDescription());
        assertEquals("1234", result2.getPassword());
        assertTrue(result2.isLocked());
        assertEquals("2", result2.getCapacity());
        /** 테스트 2, 비밀번호 있는 방 생성 종료 **/

    }

    @Test
    void saveAndGetRoomTags() {
        assertNull(roomService.saveAndGetRoomTags(null, ""));
        
        /*AutoIncreasement 때문에 room_id 얻기 힘듬*/
    }

    @Test
    void updateTags() {
        assertEquals("", roomService.updateTags(
                null,
                new String[]{"",""}).get(0).getTag().getTagName());
        assertEquals("#tag123", roomService.updateTags(
                null,
                new String[]{"#tag123"}).get(0).getTag().getTagName());
        assertEquals("#tag111", roomService.updateTags(
                null,
                new String[]{"#dummy","#tag111"}).get(1).getTag().getTagName());
    }
}