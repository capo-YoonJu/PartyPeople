package com.ssafy.api.response;

import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 *  API ([GET] /api/v1/list/tags) 요청에 대한 응답값 정의.
 */

@Getter
@Setter
@ApiModel("SuggestedKeywardRes")
public class SuggestedKeywardRes extends BaseResponseBody {
    @ApiModelProperty(name="추천 검색어", example="party")
    List<String> suggestions;

    public static SuggestedKeywardRes of(Integer statusCode, String message, List<String> suggested) {
        SuggestedKeywardRes res = new SuggestedKeywardRes();
        res.setStatusCode(statusCode);
        res.setMessage(message);
        res.setSuggestions(suggested);
        return res;
    }
}
