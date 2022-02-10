package com.armsmart.jupiter.bs.api.dto.request;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

import java.math.BigInteger;

@Data
public class ThingInputInfoAddParam {
    private String contractAddr;

    private String artName;

    private String artYear;

//    private String artKind;

    private String md5;

    private BigInteger randNum;

    private String userPubkeyM;

    private String userPubkeyE;

    private String messageIn;

    private String artSign;

    private String webStart;

    private String artistSign;

    private String artAddtional;
    //新增字段
    private String artSize;

    private Integer artWeight;

    private String artCondition;

    private String thingElement;

    private Integer thingType;
//    private String artManufacturer;

//    private String artBarcode;

    private String artDesc;

    private String author;
//    private String artNfcId;
}
