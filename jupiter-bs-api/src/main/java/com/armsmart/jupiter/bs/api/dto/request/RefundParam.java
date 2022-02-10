package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "_request")
public class RefundParam {
    private String user_id;
//    private String order_id;
    private String org_order_id;
    private Long amont;
}
