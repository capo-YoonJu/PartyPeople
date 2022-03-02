package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 파티룸 호스트 변경 API ([PATCH] /api/v1/rooms/host/{room_id}) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("RoomHostUpdateRequest")
public class RoomHostUpdateReq {
    @ApiModelProperty(name="user id(pk)", example="0")
    Long id;
    @ApiModelProperty(name="호스트 등록 여부", example="1")
    int action;
}
