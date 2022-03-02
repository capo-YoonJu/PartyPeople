package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 *  API ([GET] /api/v1/list/room~) 요청에 대한 응답값 정의.
 */

@Getter
@Setter
@ApiModel("RoomListResponse")
public class RoomListRes extends BaseResponseBody {
    @ApiModelProperty(name="파티룸 리스트")
    private Page<Room> contents;

    public static RoomListRes of(Integer statusCode, String message, Page<Room> roomList) {
        RoomListRes res = new RoomListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setContents(roomList);
        return res;
    }
}
