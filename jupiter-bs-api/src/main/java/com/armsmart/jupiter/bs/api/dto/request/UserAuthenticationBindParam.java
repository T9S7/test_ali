package com.armsmart.jupiter.bs.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 UserAuthentication修改DTO
 * @author wei.lin
 **/
@Data
public class UserAuthenticationBindParam {
    @NotNull(message = "身份证号不能为空")
    @ApiModelProperty(value = "身份证号", required = true)
    private String certificateNo;

//    @Length(max= 256,message="私钥模数长度不能超过256")
//    @ApiModelProperty("私钥模数")
//    private String privateKeyM;
//
//    @Length(max= 256,message="私钥指数长度不能超过256")
//    @ApiModelProperty("私钥指数")
//    private String privateKeyE;

    @Length(max= 256,message="公钥模数长度不能超过256")
    @ApiModelProperty("公钥模数")
    private String publicKeyM;

    @Length(max= 256,message="公钥指数长度不能超过256")
    @ApiModelProperty("公钥指数")
    private String publicKeyE;

}
