package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 接入消息推送的相关参数
 *
 * @author wei.lin
 **/
@Data
public class PushAccessParam {

    @ApiModelProperty(value = "消息推送注册ID", required = true)
    @NotBlank(message = "密消息推送注册ID不能为空")
    @Length(max = 20, message = "消息推送注册ID不能超过20位")
    protected String registrationId;

    @ApiModelProperty(value = "终端平台：0:安卓；1：IOS", required = true)
    @NotNull(message = "终端平台不能为空")
    @Range(min = 0, max = 1, message = "终端平台类型错误")
    protected Integer platform;
}
