package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 *WechatUnifiedOrderInfo详情DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class WechatUnifiedOrderInfoDetail {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("业务订单号")
    private String orderSn;

    @ApiModelProperty("应用id")
    private String appId;

    @ApiModelProperty("商户id")
    private String partnerId;

    @ApiModelProperty("预支付交易会话ID")
    private String prepayId;

    @ApiModelProperty("扩展字段")
    private String packageStr;

    @ApiModelProperty("随机字符串")
    private String nonceStr;

    @ApiModelProperty("时间戳")
    private String timeStamp;

    @ApiModelProperty("签名")
    private String sign;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

}



