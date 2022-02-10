package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ModifyOwnerAddInfoParam {
    @NotNull(message = "合约地址不能为空")
    @ApiModelProperty(value = "合约地址不能为空", required = true)
    private String contractAddr;

    @NotNull(message = "更改类别不能为空")
    @ApiModelProperty(value = "更改类别 1 卖家发货，2 买家确认收货，3 确权待定", required = true)
    private Integer modifyType;

    @NotNull(message = "用户Id不能为空")
    @ApiModelProperty(value = "需要变更的用户id", required = true)
    private Long userId;

    @NotNull(message = "随机数十六进制不为空")
    @ApiModelProperty(value = "随机数十六进制", required = true)
    private String messageIn;

    @NotNull(message = "NFC私钥签名")
    @ApiModelProperty(value = "NFC私钥签名", required = true)
    private String artSign;

    @NotNull(message = "用户私钥签名不能为空")
    @ApiModelProperty(value = "用户私钥签名", required = true)
    private String artistSign;

    @NotNull(message = "终端信息不为空")
    @ApiModelProperty(value = "终端信息", required = true)
    private String termInfo;
}
