package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * ArtPicInfo添加DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class ArtPicInfoAddParam {


    @ApiModelProperty(value = "艺术品ID", hidden = true)
    private Long artId;

    @NotBlank(message = "图片地址不能为空")
    @Length(max = 128, message = "图片地址长度不能超过128")
    @ApiModelProperty(value = "图片地址", required = true)
    private String picUrl;

    @NotBlank(message = "图片md5码不能为空")
    @Length(max = 36, message = "图片md5码长度不能超过36")
    @ApiModelProperty(value = "图片md5码", required = true)
    private String picMd5;


}



