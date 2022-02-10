package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "_request")
public class RecieverListParam {
    private String bizUserId;
    private Long amount;
}
