package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *UserInfo详情DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class UserInfoDetail {
    @ApiModelProperty("用户ID")
    private Integer id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("真实姓名")
    private String realName;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("注册时间")
    private Long registerTime;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("启用状态")
    private Boolean isEnable;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("消息推送注册ID")
    private String registrationId;

    @ApiModelProperty("终端平台：0:安卓；1：IOS")
    private Integer platform;

    @ApiModelProperty("用户类型 0：普通；1：鉴定人")
    private Integer userType;

    @ApiModelProperty("开户状态（0：未开户；1：开户中未绑定手机号；2：已完成开户）")
    private Integer accountRegisterStatus;

    @ApiModelProperty("账户保证金（分）")
    private Long deposit;

    @ApiModelProperty("资质认证状态(0-未提交，1-已通过，2-已提交待审核，3-已审核未通过)")
    private Integer checkStatus;

    @ApiModelProperty("用户个性化描述")
    private String authDesc;

    @ApiModelProperty("通联userId")
    private String tlUserId;

    @ApiModelProperty("通联电子协议编号")
    private String contractNo;

    @ApiModelProperty("是否设置通联支付密码")
    private Boolean isSetPwd;

}



