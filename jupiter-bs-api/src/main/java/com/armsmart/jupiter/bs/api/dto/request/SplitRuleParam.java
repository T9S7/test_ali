package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(description = "_request")
public class SplitRuleParam {
    private String bizUserId;
    private String accountSetNo;
    private String remark;
    private Long amount;
    private Long fee;
    private List<SplitRuleParam> splitRuleList;
}
