package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class CompanyIdQueryParam extends PageQueryParam {
    @ApiModelProperty("企业Id")
    private Integer companyId;
}
