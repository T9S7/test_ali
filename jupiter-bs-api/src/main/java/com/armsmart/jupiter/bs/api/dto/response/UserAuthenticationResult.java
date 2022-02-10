package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *UserAuthentication详情DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class UserAuthenticationResult {
    @ApiModelProperty("真实姓名")
    private String name;

    @ApiModelProperty("证件号码")
    private String certifino;

    @ApiModelProperty("身份证正面照片")
    private String certifinoFace;

    @ApiModelProperty("身份证反面照")
    private String certifinoBack;

    @ApiModelProperty("是否完成身份认证(0-未完成，1-已完成)")
    private Boolean isCert;

    @ApiModelProperty("是否绑定公钥卡（0-未绑定，1-已绑定）")
    private Boolean isBind;

    @ApiModelProperty("公钥模数")
    private String pubkeyM;

    @ApiModelProperty("公钥指数")
    private String pubkeyE;

 }
