package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.List;

/**
 * ArtInfo添加DTO
 * @author zhangrong
 **/
@Data
@ApiModel(description = "_request")
public class ArtInfoAddParam {



	@NotBlank(message="合约地址不能为空")
	@Length(max= 64,message="合约地址长度不能超过64")
	@ApiModelProperty(value = "合约地址",required = true)
	private String contractAddr;
//
//	@Length(max= 64,message="艺术品对应的NFCID长度不能超过64")
//	@ApiModelProperty("艺术品对应的NFCID")
//    private String artNfcId;

	@NotBlank(message="名称不能为空")
	@Length(max= 64,message="名称长度不能超过64")
	@ApiModelProperty(value = "名称",required = true)
	private String artName;

	@NotBlank(message="年代不能为空")
	@Length(max= 64,message="年代长度不能超过64")
	@ApiModelProperty(value = "年代",required = true)
	private String artYear;

//    @Range(min = 1, max = 7, message = "不支持的艺术品种类")
//    @NotNull(message = "艺术品种类（1.书法、2.绘画、3.瓷器、 4.古玩、5.钱币、6.明星、7.俱乐部周边）不能为空")
    @ApiModelProperty(value = "艺术品种类（1.书法、2.绘画、3.瓷器、 4.古玩、5.钱币、6.明星、7.俱乐部周边）") //, required = true
    private Integer artType;

	@Length(max= 64,message="艺术品尺寸长度不能超过64")
	@ApiModelProperty("艺术品尺寸")
    private String artSize;

	@ApiModelProperty("艺术品重量")
    private Integer artWeight;

	@ApiModelProperty("艺术品成色，0.一般、1.良好、2.优")
    private Integer artCondition;

//	@Length(max= 64,message="艺术品制造商长度不能超过64")
//	@ApiModelProperty("艺术品制造商")
//    private String artManufacturer;
//
//	@Length(max= 64,message="艺术品条码信息长度不能超过64")
//	@ApiModelProperty("艺术品条码信息")
//    private String artBarcode;

	@Length(max= 1024,message="艺术品描述长度不能超过1024")
	@ApiModelProperty("艺术品描述")
    private String artDesc;

	@NotBlank(message="作者不能为空")
	@Length(max= 64,message="作者长度不能超过64")
	@ApiModelProperty(value = "作者",required = true)
	private String author;

	@NotNull(message = "用户ID不能为空")
	@ApiModelProperty(value = "用户ID",required = true)
	private Integer userId;

//    @ApiModelProperty(value = "是否陈列到店铺")
//    private Boolean intoStore;
//
//    @ApiModelProperty(value = "艺术品价格（分）")
//    private Long artPrice;
//
//    @ApiModelProperty(value = "上架时间")
//    private Long putOnTime;

    @NotNull(message = "艺术品图片列表不能为空")
    @ApiModelProperty(value = "艺术品图片列表", required = true)
    private List<ArtPicInfoAddParam> pics;

    @NotNull(message = "随机数不能为空")
    @ApiModelProperty(value = "随机数", required = true)
    private BigInteger randNum;

    @NotBlank(message = "md5不能为空")
    @Length(max = 36, message = "md5长度不能超过36")
    @ApiModelProperty(value = "图片md5码", required = true)
    private String md5;

    @NotBlank(message = "艺术家公钥模数不能为空")
    @ApiModelProperty(value = "艺术家公钥模数", required = true)
    private String userPubkeyM;

    @NotBlank(message = "艺术家公钥指数不能为空")
    @ApiModelProperty(value = "艺术家公钥指数", required = true)
    private String userPubkeyE;

    @NotBlank(message = "随机数的16进制不能为空")
    @ApiModelProperty(value = "随机数的16进制", required = true)
    private String messageIn;

    @NotBlank(message = "用艺术品私钥对messageIn的签名不能为空")
    @ApiModelProperty(value = "用艺术品私钥对messageIn的签名", required = true)
    private String artSign;

    @NotBlank(message = "sha256不能为空")
    @ApiModelProperty(value = "sha256(artSign+artName+artYear+artKind+md5)", required = true)
    private String webStart;

    @NotBlank(message = "用艺术品私钥对webStart的签名不能为空")
    @ApiModelProperty(value = "用艺术品私钥对webStart的签名", required = true)
    private String artistSign;


}



