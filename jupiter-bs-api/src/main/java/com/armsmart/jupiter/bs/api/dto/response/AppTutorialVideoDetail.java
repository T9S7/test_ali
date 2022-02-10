package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * AppTutorialVideo详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class AppTutorialVideoDetail {
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("视频地址")
    private String videoUrl;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;

}



