package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *SysRegion详情DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class SysRegionDetail {
    @ApiModelProperty("主键")
    private Integer id;

    @ApiModelProperty("区域名称")
    private String regionName;

    @ApiModelProperty("父节点ID")
    private Integer regionParent;

/*    @ApiModelProperty("纬度")
    private String latitude;

    @ApiModelProperty("精度")
    private String longitude;*/

}



