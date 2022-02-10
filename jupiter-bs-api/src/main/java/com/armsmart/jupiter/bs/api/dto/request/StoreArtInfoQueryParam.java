package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;


/**
 * StoreArtInfo 查询参数
 *
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class StoreArtInfoQueryParam extends PageQueryParam {
    @ApiModelProperty("主键ID")
    private Long id;

    @NotNull(message = "店铺ID不能为空")
    @ApiModelProperty(value = "店铺ID", required = true)
    private Long storeId;

    @ApiModelProperty("艺术品ID")
    private Long artId;

    @ApiModelProperty("合约地址")
    private String contractAddr;

    @ApiModelProperty("名称")
    private String artName;

    @ApiModelProperty("年代")
    private String artYear;

    @ApiModelProperty("艺术品种类（1.书法、2.绘画、3.瓷器、 4.古玩、5.钱币、6.明星、7.俱乐部周边）")
    private Integer artType;

    @ApiModelProperty("艺术品描述")
    private String artDesc;

    @ApiModelProperty("作者")
    private String author;

    @ApiModelProperty("用户ID")
    private Integer userId;

    @ApiModelProperty("艺术品价格（分）")
    private Long artPrice;

    @ApiModelProperty("是否上架")
    private Boolean putOn;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

}




