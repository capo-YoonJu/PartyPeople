package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 파티룸 입장 비밀번호 확인 API ([POST] /api/v1/rooms/{room_id}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("RoomEntryPostRequest")
public class RoomEntryPostReq {
    @ApiModelProperty(name="방 비밀번호", example="방 비밀번호")
    String password;
}
