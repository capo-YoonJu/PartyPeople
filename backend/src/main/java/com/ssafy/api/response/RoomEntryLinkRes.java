package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Room;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 *  API ([GET] /api/v1/list/room~) 요청에 대한 응답값 정의.
 */

@Getter
@Setter
@ApiModel("RoomEntryRes")
public class RoomEntryLinkRes extends BaseResponseBody {
    @ApiModelProperty(name="파티룸 번호")
    Long id;
    @ApiModelProperty(name="파티룸 비밀번호 여부")
    boolean isLocked;

    public static RoomEntryLinkRes of(Integer statusCode, String message, Room room) {
        RoomEntryLinkRes res = new RoomEntryLinkRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(room.getId());
        res.setLocked(room.isLocked());
        return res;
    }
}
