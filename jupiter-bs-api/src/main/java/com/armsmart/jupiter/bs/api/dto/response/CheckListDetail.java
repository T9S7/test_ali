package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

import java.math.BigInteger;
import java.util.List;

@Data
@ApiModel(description = "_response")
public class CheckListDetail extends CheckInfoDetail {
    @ApiModelProperty("图片地址")
    private List<ThingPicInfo> pics;

    @ApiModelProperty("当前价格")
    private Long currentPrice;

    @ApiModelProperty("物品名称")
    private String artName;
}
