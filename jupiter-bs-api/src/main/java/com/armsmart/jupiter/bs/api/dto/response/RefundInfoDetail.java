package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 *RefundInfo详情DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class RefundInfoDetail {
    @ApiModelProperty("id")
    private Integer id;

    @ApiModelProperty("原始订单号")
    private String orderId;

    @ApiModelProperty("会员号")
    private Integer userId;

    @ApiModelProperty("订单金额")
    private Long amount;

    @ApiModelProperty("支付方式(1-微信，2-支付宝，3-余额，4-银行卡)")
    private Integer payMatch;

    @ApiModelProperty("退款订单状态（0-待退款；1-退款成功；2-退款失败）")
    private Integer orderStatus;

    @ApiModelProperty("createTime")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateDate;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

}



