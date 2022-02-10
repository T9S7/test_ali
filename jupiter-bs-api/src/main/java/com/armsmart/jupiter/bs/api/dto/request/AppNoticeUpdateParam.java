package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * AppNotice修改DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class AppNoticeUpdateParam {


    @NotNull(message = "主键ID不能为空")
    @ApiModelProperty(value = "主键ID", required = true)
    private Integer id;

    @NotBlank(message = "通知标题不能为空")
    @Length(max = 32, message = "通知标题长度不能超过32")
    @ApiModelProperty(value = "通知标题", required = true)
    private String title;

    @NotBlank(message = "内容不能为空")
    @Length(max = 65535, message = "内容长度不能超过65535")
    @ApiModelProperty(value = "内容", required = true)
    private String content;

    @NotBlank(message = "签名落款不能为空")
    @Length(max = 64, message = "签名落款长度不能超过64")
    @ApiModelProperty(value = "签名落款", required = true)
    private String sign;

    @NotNull(message = "类型（0：维护通知）不能为空")
    @ApiModelProperty(value = "类型（0：维护通知）", required = true)
    private Integer category;

    @NotNull(message = "是否启用不能为空")
    @ApiModelProperty(value = "是否启用", required = true)
    private Boolean enabled;
	

}



