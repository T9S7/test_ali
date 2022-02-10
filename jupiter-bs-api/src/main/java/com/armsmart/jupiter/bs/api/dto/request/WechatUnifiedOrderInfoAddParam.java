package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * WechatUnifiedOrderInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class WechatUnifiedOrderInfoAddParam {



	@NotBlank(message="业务订单号不能为空")
	@Length(max= 32,message="业务订单号长度不能超过32")
	@ApiModelProperty(value = "业务订单号",required = true)
	private String orderSn;

	@NotBlank(message="应用id不能为空")
	@Length(max= 32,message="应用id长度不能超过32")
	@ApiModelProperty(value = "应用id",required = true)
	private String appId;

	@NotBlank(message="商户id不能为空")
	@Length(max= 32,message="商户id长度不能超过32")
	@ApiModelProperty(value = "商户id",required = true)
	private String partnerId;

	@NotBlank(message="预支付交易会话ID不能为空")
	@Length(max= 64,message="预支付交易会话ID长度不能超过64")
	@ApiModelProperty(value = "预支付交易会话ID",required = true)
	private String prepayId;

	@NotBlank(message="扩展字段不能为空")
	@Length(max= 128,message="扩展字段长度不能超过128")
	@ApiModelProperty(value = "扩展字段",required = true)
	private String packageStr;

	@NotBlank(message="随机字符串不能为空")
	@Length(max= 32,message="随机字符串长度不能超过32")
	@ApiModelProperty(value = "随机字符串",required = true)
	private String nonceStr;

	@NotBlank(message="时间戳不能为空")
	@Length(max= 10,message="时间戳长度不能超过10")
	@ApiModelProperty(value = "时间戳",required = true)
	private String timeStamp;

	@NotBlank(message="签名不能为空")
	@Length(max= 64,message="签名长度不能超过64")
	@ApiModelProperty(value = "签名",required = true)
	private String sign;

	@NotNull(message = "创建时间不能为空")
	@ApiModelProperty(value = "创建时间",required = true)
	private Long createTime;

	@ApiModelProperty("修改时间")
    private Long updateTime;

	@NotNull(message = "是否删除不能为空")
	@ApiModelProperty(value = "是否删除",required = true)
	private Boolean isDel;


}



