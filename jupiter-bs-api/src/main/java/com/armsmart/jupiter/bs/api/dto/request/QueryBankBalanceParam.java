package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "_request")
public class QueryBankBalanceParam {
    private Long acctOrgType;
    private String acctNo;
    private String acctName;
    private String date;
}
