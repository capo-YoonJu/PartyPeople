package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 파티룸 사용자 리스트 API ([GET] /api/v1/rooms/users/{room_id}) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("RoomUserListGetResponse")
public class RoomUserListRes extends BaseResponseBody {
    @ApiModelProperty(name="파티룸 id")
    Long id;
    @ApiModelProperty(name="파티룸 user 정보 객체 리스트")
    List<RoomUserRes> contents = new ArrayList<>();

    public static RoomUserListRes of(Integer statusCode, String message, Long id, List<User> contents) {
        RoomUserListRes res = new RoomUserListRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setId(id);
        for (User user : contents){
            res.contents.add(RoomUserRes.of(user));
        }
        return res;
    }
}
