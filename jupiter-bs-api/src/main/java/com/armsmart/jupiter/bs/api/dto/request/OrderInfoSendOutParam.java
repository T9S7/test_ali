package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 订单发货DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class OrderInfoSendOutParam {


    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID", required = true)
    private Long orderId;

    @NotNull(message = "用户ID不能为空")
    @ApiModelProperty(value = "用户ID", required = true)
    private Integer userId;

    @NotBlank(message = "快递单号不能为空")
    @Length(max = 64, message = "快递单号长度不能超过64")
    @ApiModelProperty(value = "快递单号", required = true)
    private String expressNumber;

    @NotBlank(message = "快递公司不能为空")
    @Length(max = 20, message = "快递公司长度不能超过20")
    @ApiModelProperty(value = "快递公司", required = true)
    private String expressCompany;


}



