package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * UserInfo详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class SellerInfoDetail {
    @ApiModelProperty("卖家用户ID")
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("关注状态 -1 未关注,0 本人,1 已关注")
    private Integer focusState;

    @ApiModelProperty("卖家上架物品数")
    private Integer putOnCount;

    @ApiModelProperty("签名")
    private String authDesc;
}



