package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

/**
 * ThingInfo添加DTO
 * @author 苏礼刚
 **/
@Data
@ApiModel(description = "_request")
public class ThingInfoAddParam {
	@NotBlank(message="合约地址不能为空")
	@Length(max= 64,message="合约地址长度不能超过64")
	@ApiModelProperty(value = "合约地址",required = true)
	private String contractAddr;

	@NotBlank(message="名称不能为空")
	@Length(max= 64,message="名称长度不能超过64")
	@ApiModelProperty(value = "名称",required = true)
	private String artName;

	@NotBlank(message="年代不能为空")
	@Length(max= 64,message="年代长度不能超过64")
	@ApiModelProperty(value = "年代",required = true)
	private String artYear;

	@NotBlank(message="作者不能为空")
	@Length(max= 64,message="作者长度不能超过64")
	@ApiModelProperty(value = "作者",required = true)
	private String author;

	@NotBlank(message="描述不能为空")
	@Length(max= 64,message="描述长度不能超过64")
	@ApiModelProperty(value = "描述",required = true)
	private String thingDesc;

	@Length(max= 64,message="尺寸长度不能超过64")
	@ApiModelProperty("尺寸")
    private String thingSize;

	@ApiModelProperty("重量")
    private Integer thingWeight;

	@Length(max= 60,message="资质长度不能超过60")
	@ApiModelProperty("资质")
    private String thingCondition;

	@Length(max= 60,message="成分长度不能超过60")
	@ApiModelProperty("成分")
    private String thingElement;

	@ApiModelProperty("分类")
    private Integer thingType;

	@NotNull(message = "用户ID不能为空")
	@ApiModelProperty(value = "用户ID",required = true)
	private Integer userId;

	@NotNull(message = "图片地址不能为空")
	@ApiModelProperty(value = "图片地址不能空",required = true)
	private List<ThingPicInfoAddParam>  pics;

	@NotNull(message = "随机数不能为空")
	@ApiModelProperty(value = "随机数", required = true)
	private BigInteger randNum;

	@NotBlank(message = "md5不能为空")
	@Length(max = 36, message = "md5长度不能超过36")
	@ApiModelProperty(value = "图片md5码", required = true)
	private String md5;

	@NotBlank(message = "随机数的16进制不能为空")
	@ApiModelProperty(value = "随机数的16进制", required = true)
	private String messageIn;

	@NotBlank(message = "用户私钥对messageIn的签名不能为空")
	@ApiModelProperty(value = "用艺术品私钥对messageIn的签名", required = true)
	private String artSign;

	@NotBlank(message = "sha256不能为空")
	@ApiModelProperty(value = "sha256(artSign+artName+artYear+artKind+md5)", required = true)
	private String webStart;

	@NotBlank(message = "用物品私钥对webStart的签名不能为空")
	@ApiModelProperty(value = "用物品私钥对webStart的签名", required = true)
	private String artistSign;

}



