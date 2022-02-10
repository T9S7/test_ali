package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *WechatUnifiedOrderInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class WechatUnifiedOrderInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("业务订单号")
	private String orderSn;

	@ApiModelProperty("应用id")
	private String appId;

	@ApiModelProperty("商户id")
	private String partnerId;

	@ApiModelProperty("预支付交易会话ID")
	private String prepayId;

	@ApiModelProperty("扩展字段")
	private String packageStr;

	@ApiModelProperty("随机字符串")
	private String nonceStr;

	@ApiModelProperty("时间戳")
	private String timeStamp;

	@ApiModelProperty("签名")
	private String sign;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




