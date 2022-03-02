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
@ApiModel("CreateRoomRes")
public class CreateRoomRes extends BaseResponseBody {
    @ApiModelProperty(name="파티룸 번호", example="12")
    Long id;

    public static CreateRoomRes of(Integer statusCode, String message, Room room) {
        CreateRoomRes res = new CreateRoomRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(room.getId());
        return res;
    }
}
