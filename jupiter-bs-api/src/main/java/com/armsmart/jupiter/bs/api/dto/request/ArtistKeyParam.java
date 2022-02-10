package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@Data
public class ArtistKeyParam {
    @ApiModelProperty(value = "合约地址", required = true)
    private String contractAddr;

    @ApiModelProperty(value = "艺术家公钥模数", required = true)
    private String userPubkeyM;

    @ApiModelProperty(value = "艺术家公钥指数", required = true)
    private String userPubkeyE;

    @ApiModelProperty(value = "随机数", required = true)
    private BigInteger randNum;

    @ApiModelProperty(value = "randNum的16进制", required = true)
    private String messageIn;

    @ApiModelProperty(value = "艺术品实体签名，注：是对messageIn的签名", required = true)
    private String artWorkSign;
}
