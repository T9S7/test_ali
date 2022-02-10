package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.util.List;


/**
 *我的买家订单查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class OrderInfoBuyerParam extends PageQueryParam {


	@ApiModelProperty("订单状态（1：待支付；2：待发货；3：已发货；4：已收货；8：交易成功；9：交易失败）")
	private List<Integer> orderStatus;

}




