package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@ApiModel(description = "_request")
public class SignalAgentPayParam {
    @NotNull(message = "爱较真用户Id")
    @ApiModelProperty(value = "用户id", required = true)
    private String userId;
    private List<CollectPayParam> collectPayList;
    private String orderId;
    private Long amount;
    private Long fee;
    private List<SplitRuleParam> splitRuleList;
    private String callBackUrl;
}
