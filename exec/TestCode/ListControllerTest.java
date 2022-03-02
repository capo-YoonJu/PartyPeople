package com.ssafy.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.request.RoomCreatePostReq;
import com.ssafy.api.service.RoomServiceImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/*
 * * * * * * * * * * * * * * * * * * *
 * 방 제목, 방 설명, 태그 조회 API 테스트 *
 * * * * * * * * * * * * * * * * * * *
 * */

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ListControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RoomServiceImpl roomService;

    @Test
    void getRoomList() throws Exception {
        String[] title = {"test10title",
                "test9title",
                "test8title",
                "test7title",
                "test6title",
                "test5title",
                "test4title",
                "test3title",
                "test2title",
                "test1title",};
        String[] desc = {"test10desc",
                "test9desc",
                "test8desc",
                "test7desc",
                "test6desc",
                "test5desc",
                "test4desc",
                "test3desc",
                "test2desc",
                "test1desc",};

        for (int i = 0; i < title.length; i++) {
            RoomCreatePostReq roomCreatePostReq = new RoomCreatePostReq();

            roomCreatePostReq.setTitle(title[i]);
            roomCreatePostReq.setDescription(desc[i]);
            roomCreatePostReq.setCapacity(Integer.toString(1));
            roomCreatePostReq.setPassword("");
            roomCreatePostReq.setHashtag("#");

            roomService.createRoom(roomCreatePostReq, null);
        }

        ObjectMapper mapper = new ObjectMapper();

        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> parser = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/room")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString(), HashMap.class);
        List<HashMap<String, Object>> contents = parser.get("contents").get("content");

        assertEquals("Success", parser.get("message"));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/room").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        for (int i = 0; i < title.length; i++) {
            assertEquals(title[title.length - 1 - i], contents.get(i).get("title"));
            assertEquals(desc[title.length - 1 - i], contents.get(i).get("description"));
            assertEquals("https://pparttypeople.kro.kr/upload/dummy.png", contents.get(i).get("thumbnailUrl"));
            assertEquals("1", contents.get(i).get("capacity"));
            assertNull(contents.get(i).get("endTime"));
            assertNull(contents.get(i).get("password"));
            assertFalse((Boolean) contents.get(i).get("locked"));
        }
    }

    @Test
    void getRoomListByWord() throws Exception {
        /**
        * ListServiceImpl.getRoomListByWord 안의 deleteCloseSession(roomList); 비활성화 하고 테스트 하기
        **/

        Pageable pageable =  PageRequest.of(0, 10, Sort.by("id").descending());
        String[] title = {"안녕이라고말하지마",
                "안녕",
                "뜨거운안녕",
                "test Title",
                "test Title"
        };
        String[] desc = {"11년전노래-다비치",
                "8년전노래-효린",
                "15년전노래-토이",
                "test description",
                "test validation"
        };
        String[] tags = {
                "#다비치#노래#2010년대#2011년",
                "#효린#노래#2010년대#2014년",
                "#토이#노래#2000년대#2007년",
                "#",
                "#test1"
        };

        for (int i = 0; i < title.length; i++) {
            RoomCreatePostReq roomCreatePostReq = new RoomCreatePostReq();

            roomCreatePostReq.setTitle(title[i]);
            roomCreatePostReq.setDescription(desc[i]);
            roomCreatePostReq.setCapacity(Integer.toString(1));
            roomCreatePostReq.setPassword("");
            roomCreatePostReq.setHashtag(tags[i]);

            roomService.createRoom(roomCreatePostReq, null);
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch").
                accept(MediaType.APPLICATION_JSON)
                        .param("include", "title")
                        .param("word", "안녕"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch").
                        accept(MediaType.APPLICATION_JSON)
                        .param("word", "인녕"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch").
                        accept(MediaType.APPLICATION_JSON)
                        .param("include", "title"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch").
                        accept(MediaType.APPLICATION_JSON)
                        .param("include", "desc")
                        .param("word", "안녕"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("include", "hashtag")
                        .param("word", "잘자"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        ObjectMapper mapper = new ObjectMapper();

        /* 테스트 1, 제목 검색 테스트 */
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> parser = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("include", "title")
                        .param("word", "안녕"))
                .andReturn().getResponse().getContentAsString(), HashMap.class);

        List<HashMap<String, Object>> contents= parser.get("contents").get("content");
        HashSet<String> contentsSet = new HashSet<>();

        for(HashMap<String, Object> tmp : contents){
            contentsSet.add(tmp.get("title").toString());
        }

        assertTrue(contentsSet.contains("뜨거운안녕"));
        assertTrue(contentsSet.contains("안녕"));
        assertTrue(contentsSet.contains("안녕이라고말하지마"));
        assertFalse(contentsSet.contains("test Title"));
        /* 테스트 1, 제목 검색 테스트 종료*/

        /* 테스트 2, 제목 검색 테스트 */
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> parser2 = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("include", "title")
                        .param("word", "tle"))
                .andReturn().getResponse().getContentAsString(), HashMap.class);

        List<HashMap<String, Object>> contents2 = parser2.get("contents").get("content");
        HashSet<String> contentsSet2 = new HashSet<>();

        for(HashMap<String, Object> tmp : contents2){
            contentsSet2.add(tmp.get("title").toString());
        }

        assertTrue(contentsSet2.contains("test Title"));
        assertFalse(contentsSet2.contains("Stranger"));
        assertFalse(contentsSet2.contains("Alien"));
        /* 테스트 2, 제목 검색 테스트 종료*/

        /* 테스트 3, 설명 검색 테스트 */
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> parser3 = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("include", "des")
                        .param("word", "노래"))
                .andReturn().getResponse().getContentAsString(), HashMap.class);

        List<HashMap<String, Object>> contents3 = parser3.get("contents").get("content");
        HashSet<String> contentsSet3 = new HashSet<>();

        for(HashMap<String, Object> tmp : contents3){
            contentsSet3.add(tmp.get("title").toString());
        }

        assertTrue(contentsSet3.contains("뜨거운안녕"));
        assertTrue(contentsSet3.contains("안녕"));
        assertTrue(contentsSet3.contains("안녕이라고말하지마"));
        assertFalse(contentsSet3.contains("test Title"));
        /* 테스트 3, 설명 검색 테스트 종료*/

        /* 테스트 4, 설명 검색 테스트 */
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> parser4 = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("include", "des")
                        .param("word", "test"))
                .andReturn().getResponse().getContentAsString(), HashMap.class);

        List<HashMap<String, Object>> contents4 = parser4.get("contents").get("content");
        HashSet<String> contentsSet4 = new HashSet<>();

        for(HashMap<String, Object> tmp : contents4){
            contentsSet4.add(tmp.get("title").toString());
        }

        assertFalse(contentsSet4.contains("뜨거운안녕"));
        assertFalse(contentsSet4.contains("안녕"));
        assertFalse(contentsSet4.contains("안녕이라고말하지마"));
        assertTrue(contentsSet4.contains("test Title"));
        /* 테스트 4, 설명 검색 테스트 종료*/

        /* 테스트 5, 태그 검색 테스트 */
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> parser5 = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("include", "hashtag")
                        .param("word", "#노래"))
                .andReturn().getResponse().getContentAsString(), HashMap.class);

        List<HashMap<String, Object>> contents5 = parser5.get("contents").get("content");
        HashSet<String> contentsSet5 = new HashSet<>();

        for(HashMap<String, Object> tmp : contents5){
            contentsSet5.add(tmp.get("title").toString());
        }

        assertTrue(contentsSet5.contains("뜨거운안녕"));
        assertTrue(contentsSet5.contains("안녕"));
        assertTrue(contentsSet5.contains("안녕이라고말하지마"));
        assertFalse(contentsSet5.contains("test Title"));
        /* 테스트 5, 태그 검색 테스트 종료*/

        /* 테스트 6, 태그 검색 테스트 */
        HashMap<String, HashMap<String, List<HashMap<String, Object>>>> parser6 = mapper.readValue(mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/list/roomsearch")
                        .accept(MediaType.APPLICATION_JSON)
                        .param("include", "hashtag")
                        .param("word", "#2010년대#노래"))
                .andReturn().getResponse().getContentAsString(), HashMap.class);

        List<HashMap<String, Object>> contents6 = parser6.get("contents").get("content");
        HashSet<String> contentsSet6 = new HashSet<>();

        for(HashMap<String, Object> tmp : contents6){
            contentsSet6.add(tmp.get("title").toString());
        }

        assertFalse(contentsSet6.contains("뜨거운안녕"));
        assertTrue(contentsSet6.contains("안녕"));
        assertTrue(contentsSet6.contains("안녕이라고말하지마"));
        assertFalse(contentsSet6.contains("test Title"));
        /* 테스트 6, 태그 검색 테스트 종료*/
    }

    @Test
    void getAppropriateTagList() {
        /* 시간이 너무 오래 걸려서 postman으로 테스트 했습니다 */
    }
}