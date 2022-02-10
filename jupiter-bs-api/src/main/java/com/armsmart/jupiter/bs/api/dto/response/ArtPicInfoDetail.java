package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *ArtPicInfo详情DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_response")
public class ArtPicInfoDetail {
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("艺术品ID")
    private Long artId;

    @ApiModelProperty("图片地址")
    private String picUrl;

    @ApiModelProperty("图片md5码")
    private String picMd5;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("修改时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

}



