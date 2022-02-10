package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;


/**
 * 出价列表查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class BidInfoListQueryParam extends PageQueryParam {

    @NotBlank(message = "拍卖编号不能为空")
    @ApiModelProperty(value = "拍卖编号", required = true)
    private String sellId;


    @ApiModelProperty("竞拍人昵称")
    private String nickname;


}




