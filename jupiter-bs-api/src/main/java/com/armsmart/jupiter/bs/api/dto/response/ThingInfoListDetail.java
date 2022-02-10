package com.armsmart.jupiter.bs.api.dto.response;

import com.armsmart.jupiter.bs.api.entity.ThingInfo;
import com.armsmart.jupiter.bs.api.entity.ThingSellInfo;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(description = "_response")
public class ThingInfoListDetail {
    private ThingInfo thingInfo;
    private ThingSellInfo thingSellInfo;
}
