package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wei.lin
 * @date 2021/12/14
 */
@Data
@ApiModel(description = "_response")
public class OtherUserInfoDetail {

    @ApiModelProperty("用户id")
    private Integer userId;

    @ApiModelProperty("参拍数")
    private Integer bidCount;

    @ApiModelProperty("关注数")
    private Integer focusCount;

    @ApiModelProperty("粉丝数")
    private Integer fansCount;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户签名")
    private String authDesc;

    @ApiModelProperty("关注状态 -1 未关注,0 本人,1 粉丝 2互相关注")
    private Integer focusState;
}
