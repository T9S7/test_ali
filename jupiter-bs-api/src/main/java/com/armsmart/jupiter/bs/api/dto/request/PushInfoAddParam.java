package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * PushInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class PushInfoAddParam {



	@NotNull(message = "用户id不能为空")
	@ApiModelProperty(value = "用户id",required = true)
	private Integer userId;

	@Length(max= 11,message="手机号长度不能超过11")
	@ApiModelProperty("手机号")
    private String mobile;

	@ApiModelProperty("平台 0:安卓；1：IOS")
    private Integer platform;

	@NotBlank(message="消息推送注册ID不能为空")
	@Length(max= 20,message="消息推送注册ID长度不能超过20")
	@ApiModelProperty(value = "消息推送注册ID",required = true)
	private String registrationId;

	@NotNull(message = "消息类型(1.确认签名，2.送评单消息，3.校验消息，4.交易消息，5.系统消息)不能为空")
	@ApiModelProperty(value = "消息类型(1.确认签名，2.送评单消息，3.校验消息，4.交易消息，5.系统消息)",required = true)
	private Integer pushType;

	@NotBlank(message="推送消息不能为空")
	@Length(max= 200,message="推送消息长度不能超过200")
	@ApiModelProperty(value = "推送消息",required = true)
	private String pushInfo;

	@Length(max= 200,message="消息的扩展字段长度不能超过200")
	@ApiModelProperty("消息的扩展字段")
    private String extras;

	@ApiModelProperty("推送id")
    private Integer sendno;

	@Length(max= 200,message="msg Id长度不能超过200")
	@ApiModelProperty("msg Id")
    private String msgId;

	@NotNull(message = "是否已读（0.未读，1.已读）不能为空")
	@ApiModelProperty(value = "是否已读（0.未读，1.已读）",required = true)
	private Boolean isRead;

	@NotNull(message = "创建时间不能为空")
	@ApiModelProperty(value = "创建时间",required = true)
	private Long createTime;

	@ApiModelProperty("updateTime")
    private Long updateTime;

	@ApiModelProperty("阅读时间")
    private Long readTime;

	@NotNull(message = "是否删除不能为空")
	@ApiModelProperty(value = "是否删除",required = true)
	private Boolean isDel;


}



