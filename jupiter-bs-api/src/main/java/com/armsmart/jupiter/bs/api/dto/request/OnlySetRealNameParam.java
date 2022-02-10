package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class OnlySetRealNameParam {
    @NotNull(message = "爱较真userId")
    @ApiModelProperty(value = "爱较真userId", required = true)
    private String userId;

    @NotNull(message = "用户姓名")
    @ApiModelProperty(value = "用户姓名", required = true)
    private String name;

    @NotNull(message = "证件号码不为空")
    @ApiModelProperty(value = "证件号码", required = true)
    private String identityNo;
}
