package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;

/**
 * 商品管理-查询参数
 *
 * @author wei.lin
 * @date 2021/12/21
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class GoodsQueryParam extends PageQueryParam {

    @NotNull
    @ApiModelProperty(value = "当前状态(1售卖中，9已下架)", required = true)
    private Integer currentState;
}
