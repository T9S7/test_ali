package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import javax.validation.constraints.NotBlank;

@Data
public class InstanceDepolyParam {
    @NotBlank(message = "发布类型")
    @ApiModelProperty(value = "发布类型 auth 鉴权；deal 交易", required = true)
    private String instanceType;

    @NotBlank(message = "是否华夏卡")
    @ApiModelProperty(value = "是否华夏卡 0.不是 1.是 默认0", required = true)
    private Integer isHuaXiaCard;
}
