package com.ssafy.api.service;

import com.ssafy.db.entity.Room;
import com.ssafy.db.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
* * * * * * * * * * * * * * * * *
* 방 제목, 방 설명, 태그 조회 테스트 *
* * * * * * * * * * * * * * * * *
* */

@SpringBootTest
@Transactional
class ListServiceImplTest {
    @Autowired
    private RoomRepository roomRepository;

    @BeforeAll
    static void prepareDate() {
    }

    private void insertData() {
        // 방 정보 입력
        Room[] room = new Room[10];

        for (int i = 0; i < room.length; i++) {
            room[i] = new Room();
        }

        room[0].setTitle("test1title");
        room[0].setDescription("test1desc");
        room[0].setCapacity("1");

        room[1].setTitle("test2title");
        room[1].setDescription("test2desc");
        room[1].setCapacity("2");

        room[2].setTitle("test3title");
        room[2].setDescription("test3desc");
        room[2].setCapacity("1");

        room[3].setTitle("test4title");
        room[3].setDescription("test4desc");
        room[3].setCapacity("-1");

        room[4].setTitle("test5title");
        room[4].setDescription("test5desc");
        room[4].setCapacity("11");

        room[5].setTitle("test6title");
        room[5].setDescription("test6desc");
        room[5].setCapacity("11");

        room[6].setTitle("test7title");
        room[6].setDescription("test7desc");
        room[6].setCapacity("-1");

        room[7].setTitle("test8title");
        room[7].setDescription("test8desc");
        room[7].setCapacity("6");

        room[8].setTitle("test9title");
        room[8].setDescription("test9desc");
        room[8].setCapacity("6");

        room[9].setTitle("test10title");
        room[9].setDescription("test10desc");
        room[9].setCapacity("8");

        for (int i = 0; i < room.length; i++) {
            roomRepository.save(room[i]);
        }
    }

    @Test
    void getRoomList() {
        this.insertData();
        String[] title = {"test1title",
                "test2title",
                "test3title",
                "test4title",
                "test5title",
                "test6title",
                "test7title",
                "test8title",
                "test9title",
                "test10title",};
        String[] desc = {"test1desc",
                "test2desc",
                "test3desc",
                "test4desc",
                "test5desc",
                "test6desc",
                "test7desc",
                "test8desc",
                "test9desc",
                "test10desc",};
        List<Room> data = roomRepository.findAll();

        for (int i = 0;i < title.length;i++) {
            assertEquals(title[i], data.get(data.size() - title.length + i).getTitle());
            assertEquals(desc[i], data.get(data.size() - desc.length + i).getDescription());
        }
    }
}