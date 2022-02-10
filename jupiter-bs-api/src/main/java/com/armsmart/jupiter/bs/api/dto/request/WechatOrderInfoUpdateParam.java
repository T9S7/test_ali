package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 WechatOrderInfo修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class WechatOrderInfoUpdateParam {


	@NotNull(message = "id不能为空")
	@ApiModelProperty(value = "id", required = true)
	private Integer id;

	@ApiModelProperty("微信支付分配的终端设备号")
	private String deviceInfo;

	@ApiModelProperty("用户标识")
	private String openid;

	@ApiModelProperty("用户是否关注公众账号，Y-关注，N-未关注")
	private String isSubscribe;

	@ApiModelProperty("交易类型")
	private String tradeType;

	@ApiModelProperty("交易状态")
	private String tradeState;

	@ApiModelProperty("付款银行")
	private String bankType;

	@ApiModelProperty("总金额")
	private Integer totalFee;

	@ApiModelProperty("货币种类")
	private String feeType;

	@ApiModelProperty("现金支付金额")
	private Integer cashFee;

	@ApiModelProperty("现金支付货币类型")
	private String cashFeeType;

	@ApiModelProperty("应结订单金额")
	private Integer settlementTotalFee;

	@ApiModelProperty("代金券金额")
	private Integer couponFee;

	@ApiModelProperty("代金券使用数量")
	private Integer couponCount;

	@ApiModelProperty("代金券ID")
	private String couponIdNo;

	@ApiModelProperty("代金券类型")
	private String couponTypeNo;

	@ApiModelProperty("单个代金券支付金额")
	private String couponFeeNo;

	@ApiModelProperty("微信支付订单号")
	private String transactionId;

	@ApiModelProperty("商户订单号")
	private String outTradeNo;

	@ApiModelProperty("附加数据")
	private String attach;

	@ApiModelProperty("支付完成时间")
	private String timeEnd;

	@ApiModelProperty("交易状态描述")
	private String tradeStateDesc;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;


}



