package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * 我的出价 查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class BidInfoMyQueryParam extends PageQueryParam {

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;


}




