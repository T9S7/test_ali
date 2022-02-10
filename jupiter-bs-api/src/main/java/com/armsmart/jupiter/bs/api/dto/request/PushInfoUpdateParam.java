package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 PushInfo修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class PushInfoUpdateParam {


	@NotNull(message = "主键ID不能为空")
	@ApiModelProperty(value = "主键ID", required = true)
	private Integer id;

	@ApiModelProperty("用户id")
	private Integer userId;

	@ApiModelProperty("手机号")
	private String mobile;

	@ApiModelProperty("平台 0:安卓；1：IOS")
	private Integer platform;

	@ApiModelProperty("消息推送注册ID")
	private String registrationId;

	@ApiModelProperty("消息类型(1.确认签名，2.送评单消息，3.校验消息，4.交易消息，5.系统消息)")
	private Integer pushType;

	@ApiModelProperty("推送消息")
	private String pushInfo;

	@ApiModelProperty("消息的扩展字段")
	private String extras;

	@ApiModelProperty("推送id")
	private Integer sendno;

	@ApiModelProperty("msg Id")
	private String msgId;

	@ApiModelProperty("是否已读（0.未读，1.已读）")
	private Boolean isRead;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("updateTime")
	private Long updateTime;

	@ApiModelProperty("阅读时间")
	private Long readTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



