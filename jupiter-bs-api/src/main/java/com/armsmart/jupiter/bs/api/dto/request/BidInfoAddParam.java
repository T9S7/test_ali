package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * BidInfo添加DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class BidInfoAddParam {

    @Range(min = 100, message = "起拍价不能低于1元")
    @NotNull(message = "竞标价格（分）不能为空")
    @ApiModelProperty(value = "竞标价格（分）", required = true)
    private Long bidPrice;

    @NotBlank(message = "拍卖编号不能为空")
    @ApiModelProperty(value = "拍卖编号", required = true)
    private String sellId;

    @NotNull(message = "竞拍人ID不能为空")
    @ApiModelProperty(value = "竞拍人ID", required = true)
    private Integer userId;

    @NotBlank(message = "竞拍人昵称不能为空")
    @Length(max = 16, message = "竞拍人昵称长度不能超过16")
    @ApiModelProperty(value = "竞拍人昵称", required = true)
    private String nickname;



}



