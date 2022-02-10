package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 上架请求
 *
 * @author 林伟
 **/
@Data
@ApiModel(description = "_request")
public class ProductInfoPutOnParam {


    @NotBlank(message = "产品名称不能为空")
    @Length(max = 50, message = "产品名称长度不能超过50")
    @ApiModelProperty(value = "产品名称", required = true)
    private String name;

    @ApiModelProperty("一级类型")
    private Integer typeLevelOne;

    @ApiModelProperty("二级类型")
    private Integer typeLevelTwo;

    @Length(max = 100, message = "作者长度不能超过100")
    @ApiModelProperty("作者")
    private String author;

    @Length(max = 100, message = "拥有者长度不能超过100")
    @ApiModelProperty("拥有者")
    private String owner;

    @NotBlank(message = "序号不能为空")
    @Length(max = 100, message = "序号长度不能超过100")
    @ApiModelProperty(value = "序号", required = true)
    private String serialNum;

    @NotBlank(message = "介绍不能为空")
    @Length(max = 300, message = "介绍长度不能超过300")
    @ApiModelProperty(value = "介绍", required = true)
    private String description;

    @NotBlank(message = "规格不能为空")
    @Length(max = 100, message = "规格长度不能超过100")
    @ApiModelProperty(value = "规格", required = true)
    private String specs;

    @NotNull(message = "库存不能为空")
    @ApiModelProperty(value = "库存", required = true)
    private Integer stock;

    @NotNull(message = "图片地址列表不能为空")
    @ApiModelProperty(value = "图片地址列表", required = true)
    private List<String> pics;

    @ApiModelProperty(value = "视频地址列表")
    private List<String> videos;
}



