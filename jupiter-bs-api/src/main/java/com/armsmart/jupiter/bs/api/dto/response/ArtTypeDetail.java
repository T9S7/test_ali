package com.armsmart.jupiter.bs.api.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *ArtType详情DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_response")
public class ArtTypeDetail {
    @ApiModelProperty("序号")
    private Integer id;

    @ApiModelProperty("级别")
    private Integer lever;

    @ApiModelProperty("类别名称")
    private String typeName;

    @ApiModelProperty("父id")
    private Integer parentId;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("更新时间")
    private Long updateTime;

    @ApiModelProperty("是否删除")
    private Boolean isDel;

    @ApiModelProperty("类型子集")
    private List<ArtTypeDetail> children;
}



