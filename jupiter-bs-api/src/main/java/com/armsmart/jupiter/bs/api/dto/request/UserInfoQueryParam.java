package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *UserInfo 查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class UserInfoQueryParam extends PageQueryParam {
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

	@ApiModelProperty("消息推送注册ID")
	private String registrationId;

	@ApiModelProperty("终端平台：0:安卓；1：IOS")
	private Integer platform;

	@ApiModelProperty("用户类型 0：普通；1：鉴定人")
	private Integer userType;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

	@ApiModelProperty("账户保证金（分）")
	private Long deposit;

	@ApiModelProperty("开户状态（0：未开户；1：开户中未绑定手机号；2：已完成开户）")
	private Integer accountRegisterStatus;

}




