package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class ApplyBindBankCardParam {
    @NotNull(message = "爱较真用户Id")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @NotNull(message = "手机号")
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    @NotNull(message = "银行卡号")
    @ApiModelProperty(value = "银行卡号", required = true)
    private String cardNo;

    @NotNull(message = "用户姓名")
    @ApiModelProperty(value = "用户姓名", required = true)
    private String name;

    @NotNull(message = "绑卡方式  默认传7")
    @ApiModelProperty(value = "绑卡方式 默认传7,借记卡传3", required = true)
    private Long cardCheck;

    @NotNull(message = "身份证号码")
    @ApiModelProperty(value = "身份证号码", required = true)
    private String identityNo;

// 绑卡方式7   暂不传
//    @ApiModelProperty("有效期")
//    private String validate;
//
//    @ApiModelProperty("cvv2")
//    private String cvv2;
//
//    @ApiModelProperty("支付行号")
//    private String unionBank;
}
