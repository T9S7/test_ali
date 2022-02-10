package com.armsmart.jupiter.bs.api.dto.request;

import com.armsmart.jupiter.bs.api.entity.ThingPicInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "_request")
public class NfcInfoLoadParam {
    @NotNull(message = "物品描述不可为空")
    @ApiModelProperty(value = "物品描述", required = true)
    private String thingDesc;

    @NotNull(message = "价格不能为空")
    @ApiModelProperty(value = "芯片价格(分)", required = true)
    private Long thingPrice;

    @NotNull(message = "类别不能为空")
    @ApiModelProperty(value = "类别不能为空 0 孚贴，1 读卡器", required = true)
    private Integer thingType;

    @NotNull(message = "芯片图片不能为空")
    @ApiModelProperty(value = "芯片图片集", required = true)
    private List<ThingPicInfoAddParam> pics;
}
