package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserIdQueryParam extends PageQueryParam {
    @ApiModelProperty("用户id")
    private Long userId;
}
