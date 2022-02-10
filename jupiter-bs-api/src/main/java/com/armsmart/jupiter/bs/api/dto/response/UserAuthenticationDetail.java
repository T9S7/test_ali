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
public class UserAuthenticationDetail {
    @ApiModelProperty("序列号")
    private Long id;

    @ApiModelProperty("真实姓名")
    private String name;

    @ApiModelProperty("APP用户编号")
    private Integer userId;

    @ApiModelProperty("证件号码")
    private String certifino;

    @ApiModelProperty("手机号")
    private String mobile;

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

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

}



