package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *AppVersion 查询参数
 * @author wei.lin
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class AppVersionQueryParam extends PageQueryParam {
	@ApiModelProperty("id")
	private Integer id;

	@ApiModelProperty("平台 0 安卓，1 ios")
	private Integer platform;

	@ApiModelProperty("是否强制更新（0--否，1--是）")
	private Boolean isForceUpdate;

	@ApiModelProperty("版本code")
	private Integer versionCode;

	@ApiModelProperty("版本号")
	private String versionNum;

	@ApiModelProperty("版本描述")
	private String versionDes;

	@ApiModelProperty("程序下载地址")
	private String downloadUrl;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("更新时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




