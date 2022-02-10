package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * SysRegion添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class SysRegionAddParam {



	@Length(max= 32,message="区域名称长度不能超过32")
	@ApiModelProperty("区域名称")
    private String regionName;

	@ApiModelProperty("父节点ID")
    private Integer regionParent;

	@Length(max= 32,message="纬度长度不能超过32")
	@ApiModelProperty("纬度")
    private String latitude;

	@Length(max= 32,message="精度长度不能超过32")
	@ApiModelProperty("精度")
    private String longitude;


}



