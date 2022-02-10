package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *ArtInfo 查询参数
 * @author 苏礼刚
 **/
@Data
@EqualsAndHashCode(callSuper = false)
public class ArtInfoQueryParam extends PageQueryParam {
	@ApiModelProperty("艺术品ID")
	private Long artId;

	@ApiModelProperty("合约地址")
	private String contractAddr;

	@ApiModelProperty("艺术品对应的NFCID")
	private String artNfcId;

	@ApiModelProperty("名称")
	private String artName;

	@ApiModelProperty("年代")
	private String artYear;

	@ApiModelProperty("艺术品种类（1.书法、2.绘画、3.瓷器、 4.古玩、5.钱币、6.明星、7.俱乐部周边）")
	private Integer artType;

	@ApiModelProperty("艺术品尺寸")
	private String artSize;

	@ApiModelProperty("艺术品重量")
	private Integer artWeight;

	@ApiModelProperty("艺术品成色，0.一般、1.良好、2.优")
	private Integer artCondition;

	@ApiModelProperty("艺术品制造商")
	private String artManufacturer;

	@ApiModelProperty("艺术品条码信息")
	private String artBarcode;

	@ApiModelProperty("艺术品描述")
	private String artDesc;

	@ApiModelProperty("作者")
	private String author;

	@ApiModelProperty("用户ID")
	private Integer userId;

	@ApiModelProperty("创建时间")
	private Long createTime;

	@ApiModelProperty("修改时间")
	private Long updateTime;

	@ApiModelProperty("是否删除")
	private Boolean isDel;

}




