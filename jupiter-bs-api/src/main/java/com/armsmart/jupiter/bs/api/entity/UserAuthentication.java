package com.armsmart.jupiter.bs.api.entity;
import lombok.Data;


/**
 *UserAuthentication entity
 * @author wei.lin
 **/
@Data
public class UserAuthentication {
    /**
    *序列号
    */
    private Long id;

    /**
    *真实姓名
    */
    private String name;

    /**
    *APP用户编号
    */
    private Integer userId;

    /**
    *证件号码
    */
    private String certificateNo;

    /**
    *手机号
    */
    private String mobile;

    /**
    *身份证正面照片
    */
    private String certificateFace;

    /**
    *身份证反面照
    */
    private String certificateBack;

    /**
    *是否完成身份认证(0-未完成，1-已完成)
    */
    private Boolean isCert;

    /**
    *是否绑定公钥卡（0-未绑定，1-已绑定）
    */
    private Boolean isBind;

    /**
     *私钥模数
     */
    private String privateKeyM;

    /**
     *私钥指数
     */
    private String privateKeyE;

    /**
    *公钥模数
    */
    private String publicKeyM;

    /**
    *公钥指数
    */
    private String publicKeyE;

    /**
    *创建时间
    */
    private Long createTime;

    /**
    *更新时间
    */
    private Long updateTime;

    /**
    *是否删除
    */
    private Boolean isDel;


}




