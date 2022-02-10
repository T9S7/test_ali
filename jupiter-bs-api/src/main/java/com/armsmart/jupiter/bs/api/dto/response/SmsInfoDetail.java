package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *SmsInfo详情DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class SmsInfoDetail {
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("短信类型（1：注册）")
    private Integer smsType;

    @ApiModelProperty("验证码")
    private String verifyCode;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("过期时间")
    private Long expireTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

}



