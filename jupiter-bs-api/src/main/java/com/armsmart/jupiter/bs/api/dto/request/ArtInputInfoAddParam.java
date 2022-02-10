package com.armsmart.jupiter.bs.api.dto.request;

import lombok.Data;

import java.math.BigInteger;


/**
 * CoinInputInfo添加DTO
 *
 * @author wei.lin
 **/
@Data
public class ArtInputInfoAddParam {

    private String contractAddr;

    private String artName;

    private String artYear;

    private String artKind;

    private Long artPrice;

    private String md5;

    private BigInteger randNum;

    private String userPubkeyM;

    private String userPubkeyE;

    private String messageIn;

    private String artSign;

    private String webStart;

    private String artistSign;

    //新增字段
    private String artSize;

    private Integer artWeight;

    private String artCondition;

    private String artManufacturer;

    private String artBarcode;

    private String artDesc;

    private String artNfcId;


}



