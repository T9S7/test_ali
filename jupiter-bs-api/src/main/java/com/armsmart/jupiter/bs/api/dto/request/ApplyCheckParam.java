package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * ApplyCheck添加DTO
 *
 * @author dong.chu
 **/
@Data
@ApiModel(description = "_request")
public class ApplyCheckParam {

    @NotBlank(message = "合约地址不能为空")
    @ApiModelProperty(value = "合约地址", required = true)
    private String contractAddr;

    @NotBlank(message = "签名信息不能为空")
    @ApiModelProperty(value = "签名信息", required = true)
    private String signatureIn;

    @NotBlank(message = "鉴权时间不能为空")
    @ApiModelProperty(value = "鉴权时间", required = true)
    private String checkTime;

    @NotBlank(message = "randHex")
    @ApiModelProperty(value = "随机数的十六进制", required = true)
    private String randHex;

//    @NotBlank(message = "鉴权地点不能为空")
//    @ApiModelProperty(value = "鉴权地点", required = true)
//    private String checkAddr;

    @NotBlank(message = "终端信息不能为空")
    @ApiModelProperty(value = "终端信息", required = true)
    private String termInfo;

    @ApiModelProperty(value = "用户id", required = true)
    private Long userId;
}
