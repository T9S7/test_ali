package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * WechatOrderInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class WechatOrderInfoAddParam {



	@Length(max= 32,message="微信支付分配的终端设备号长度不能超过32")
	@ApiModelProperty("微信支付分配的终端设备号")
    private String deviceInfo;

	@NotBlank(message="用户标识不能为空")
	@Length(max= 128,message="用户标识长度不能超过128")
	@ApiModelProperty(value = "用户标识",required = true)
	private String openid;

	@NotBlank(message="用户是否关注公众账号，Y-关注，N-未关注不能为空")
	@Length(max= 1,message="用户是否关注公众账号，Y-关注，N-未关注长度不能超过1")
	@ApiModelProperty(value = "用户是否关注公众账号，Y-关注，N-未关注",required = true)
	private String isSubscribe;

	@NotBlank(message="交易类型不能为空")
	@Length(max= 16,message="交易类型长度不能超过16")
	@ApiModelProperty(value = "交易类型",required = true)
	private String tradeType;

	@NotBlank(message="交易状态不能为空")
	@Length(max= 32,message="交易状态长度不能超过32")
	@ApiModelProperty(value = "交易状态",required = true)
	private String tradeState;

	@NotBlank(message="付款银行不能为空")
	@Length(max= 16,message="付款银行长度不能超过16")
	@ApiModelProperty(value = "付款银行",required = true)
	private String bankType;

	@NotNull(message = "总金额不能为空")
	@ApiModelProperty(value = "总金额",required = true)
	private Integer totalFee;

	@Length(max= 8,message="货币种类长度不能超过8")
	@ApiModelProperty("货币种类")
    private String feeType;

	@NotNull(message = "现金支付金额不能为空")
	@ApiModelProperty(value = "现金支付金额",required = true)
	private Integer cashFee;

	@Length(max= 16,message="现金支付货币类型长度不能超过16")
	@ApiModelProperty("现金支付货币类型")
    private String cashFeeType;

	@ApiModelProperty("应结订单金额")
    private Integer settlementTotalFee;

	@ApiModelProperty("代金券金额")
    private Integer couponFee;

	@ApiModelProperty("代金券使用数量")
    private Integer couponCount;

	@Length(max= 100,message="代金券ID长度不能超过100")
	@ApiModelProperty("代金券ID")
    private String couponIdNo;

	@Length(max= 100,message="代金券类型长度不能超过100")
	@ApiModelProperty("代金券类型")
    private String couponTypeNo;

	@Length(max= 100,message="单个代金券支付金额长度不能超过100")
	@ApiModelProperty("单个代金券支付金额")
    private String couponFeeNo;

	@NotBlank(message="微信支付订单号不能为空")
	@Length(max= 32,message="微信支付订单号长度不能超过32")
	@ApiModelProperty(value = "微信支付订单号",required = true)
	private String transactionId;

	@NotBlank(message="商户订单号不能为空")
	@Length(max= 32,message="商户订单号长度不能超过32")
	@ApiModelProperty(value = "商户订单号",required = true)
	private String outTradeNo;

	@Length(max= 128,message="附加数据长度不能超过128")
	@ApiModelProperty("附加数据")
    private String attach;

	@NotBlank(message="支付完成时间不能为空")
	@Length(max= 14,message="支付完成时间长度不能超过14")
	@ApiModelProperty(value = "支付完成时间",required = true)
	private String timeEnd;

	@NotBlank(message="交易状态描述不能为空")
	@Length(max= 256,message="交易状态描述长度不能超过256")
	@ApiModelProperty(value = "交易状态描述",required = true)
	private String tradeStateDesc;

	@ApiModelProperty("创建时间")
    private Long createTime;

	@ApiModelProperty("更新时间")
    private Long updateTime;

	@ApiModelProperty("是否删除")
    private Boolean isDel;


}



