package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * AppNotice详情DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class AppNoticeDetail {
    @ApiModelProperty("主键ID")
    private Integer id;

    @ApiModelProperty("通知标题")
    private String title;

    @ApiModelProperty("内容")
    private String content;

    @ApiModelProperty("签名落款")
    private String sign;

    @ApiModelProperty("类型（0：维护通知）")
    private Integer category;

    @ApiModelProperty("是否启用")
    private Boolean enabled;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;


}



