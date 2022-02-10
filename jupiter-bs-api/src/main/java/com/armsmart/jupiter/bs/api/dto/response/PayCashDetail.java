package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.common.api.CommonResult;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class PayCashDetail {
    @ApiModelProperty("是否重复支持")
    private Integer payAgain;

    @ApiModelProperty("返回信息")
    private Object commonResult;

    @ApiModelProperty("url")
    private String url;

    @ApiModelProperty("是否设置支付密码")
    private Integer ifSetPwd;
}
