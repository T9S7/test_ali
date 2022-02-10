package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "_request")
public class QueryInExpDetailParam {
    @NotNull(message = "爱较真用户Id")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;

    @NotNull(message = "查询开始时间")
    @ApiModelProperty(value = "查询开始时间 -- yyyy-MM-dd", required = true)
    private String dateStart;

    @NotNull(message = "查询结束时间")
    @ApiModelProperty(value = "查询结束时间 --yyyy-MM-dd 起始时间，结束时间的区间差不能超过31天（含起始、结束）建议跨度不超过7天", required = true)
    private String dateEnd;

    @NotNull(message = "起始位置")
    @ApiModelProperty(value = "起始位置 取值>0")
    private Long startPosition;

    @NotNull(message = "查询条数")
    @ApiModelProperty(value = "起始位置起开始查询条数")
    private Long queryNum;
}
