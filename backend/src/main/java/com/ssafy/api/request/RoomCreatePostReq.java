package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * 파티룸 생성 API ([POST] /api/v1/rooms) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ToString
@ApiModel("RoomCreatePostRequest")
public class RoomCreatePostReq {
	@ApiModelProperty(name="방 제목", example="ssafy 6기 모여라")
	String title;
	@ApiModelProperty(name="방 설명", example="공통 프로젝트 뒤풀이합니다")
	String description;
	@ApiModelProperty(name="최대 인원수", example="8")
	String capacity;
	@ApiModelProperty(name="비밀번호", example="1234")
	String password;
	@ApiModelProperty(name="해시태그", example="#ssafy#파티피플")
	String hashtag;
}
