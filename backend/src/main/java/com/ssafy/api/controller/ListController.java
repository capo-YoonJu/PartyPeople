package com.ssafy.api.controller;

import com.ssafy.api.response.RoomListRes;
import com.ssafy.api.response.SuggestedKeywardRes;
import com.ssafy.api.service.ListService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 파티룸 리스트, 태그 관련 API 요청 처리를 위한 컨트롤러 정의.
 */
@Api(value = "파티룸 리스트, 태그 API", tags = {"List"})
@RestController
@RequestMapping("/api/v1/list")
public class ListController {
	
	@Autowired
	private ListService listService;

	@GetMapping("/room")
	@ApiOperation(value = "미팅 룸 정보 조회", notes = "page, size 파라메터를 사용하여 미팅 룸 정보를 조회한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공")
	})
	public ResponseEntity<? extends BaseResponseBody> getRoomList(final Pageable pageable) {
		Page<Room> roomList = listService.getRoomList(pageable);
		return ResponseEntity.status(200).body(RoomListRes.of(200, "Success", roomList));
	}

	@GetMapping("/roomsearch")
	@ApiOperation(value = "미팅 룸 정보 검색", notes = "word, include, page, size 파라메터를 사용하여 미팅 룸 정보를 조회한다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공")
	})
	public ResponseEntity<? extends BaseResponseBody> getRoomListByWord(
			@RequestParam @ApiParam(value = "검색어", required = true) String word,
			@RequestParam @ApiParam(value = "검색 키워드(제목 : title, 내용 : des, 해시태그 : hashtag)", required = true)  String include,
			final Pageable pageable) {

		Page<Room> roomList = listService.getRoomListByWord(word, include, pageable);
		return ResponseEntity.status(200).body(RoomListRes.of(200, "Success", roomList));
	}

	@GetMapping("/suggestion")
	@ApiOperation(value = "태그 정보 반환", notes = "현재 입력 단어와 전방 일치하는 태그 반환.")
	@ApiResponses({
			@ApiResponse(code = 200, message = "성공")
	})
	public ResponseEntity<? extends BaseResponseBody> getAppropriateTagList(
			@RequestParam @ApiParam(value = "검색어", required = true) String word,
			@RequestParam @ApiParam(value = "검색 키워드(제목 : title, 내용 : des, 해시태그 : hashtag)", required = true)  String include) {

		List<String> suggestion = listService.getRelativeKeyward(include, word);

		return ResponseEntity.status(200).body(SuggestedKeywardRes.of(200, "Success", suggestion));
	}
}
