package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;


import javax.validation.constraints.NotNull;

/**
 * UserInfo添加DTO
 * @author wei.lin
 **/
@Data
@ApiModel(description = "_request")
public class UserInfoAddParam {



	@Length(max= 16,message="昵称长度不能超过16")
	@ApiModelProperty("昵称")
    private String nickname;

	@Length(max= 255,message="头像长度不能超过255")
	@ApiModelProperty("头像")
    private String avatar;

	@Length(max= 16,message="真实姓名长度不能超过16")
	@ApiModelProperty("真实姓名")
    private String realName;

	@Length(max= 64,message="邮箱长度不能超过64")
	@ApiModelProperty("邮箱")
    private String email;

	@NotNull(message = "注册时间不能为空")
	@ApiModelProperty(value = "注册时间",required = true)
	private Long registerTime;

	@NotBlank(message="手机号不能为空")
	@Length(max= 16,message="手机号长度不能超过16")
	@ApiModelProperty(value = "手机号",required = true)
	private String mobile;

	@NotNull(message = "启用状态不能为空")
	@ApiModelProperty(value = "启用状态",required = true)
	private Boolean isEnable;

	@ApiModelProperty("修改时间")
    private Long updateTime;

	@NotNull(message = "是否删除不能为空")
	@ApiModelProperty(value = "是否删除",required = true)
	private Boolean isDel;


}



