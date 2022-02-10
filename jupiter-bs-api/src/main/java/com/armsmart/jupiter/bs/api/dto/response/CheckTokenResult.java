package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wei.lin
 * @date 2021/9/23
 */
@Data
public class CheckTokenResult {

    @ApiModelProperty("是否即将过期")
    private Boolean outOfDate;
    @ApiModelProperty("刷新后的token")
    private String refreshToken;

}
