package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 AppConfigureInfo修改DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ApplicationTransferParam {
    private String userId;
    private Long amount;
}
