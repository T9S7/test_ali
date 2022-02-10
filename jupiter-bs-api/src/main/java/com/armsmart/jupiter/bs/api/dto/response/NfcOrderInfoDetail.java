package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class NfcOrderInfoDetail extends OrderInfoDetail {
    private Boolean isSend;
}
