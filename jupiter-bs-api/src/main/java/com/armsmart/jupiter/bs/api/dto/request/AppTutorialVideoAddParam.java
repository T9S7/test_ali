package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * AppTutorialVideo添加DTO
 *
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class AppTutorialVideoAddParam {

    @NotBlank(message = "视频地址不能为空")
    @Length(max = 255, message = "视频地址长度不能超过255")
    @ApiModelProperty(value = "视频地址", required = true)
    private String videoUrl;


}



