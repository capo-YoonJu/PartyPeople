package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 회원 본인 정보 조회 API ([GET] /api/v1/users/me) 요청에 대한 응답값 정의.
 */
@Getter
@Setter
@ApiModel("UserResponse")
public class UserRes extends BaseResponseBody{
	@ApiModelProperty(name="User ID")
	String accountId;
	@ApiModelProperty(name="User Nickname")
	String nickname;

	public static UserRes of(Integer statusCode, String message, User user) {
		UserRes res = new UserRes();
		res.setStatusCode(statusCode);
		res.setMessage(message);
		res.setAccountId(user.getAccountId());
		res.setNickname(user.getNickname());
		return res;
	}
}
