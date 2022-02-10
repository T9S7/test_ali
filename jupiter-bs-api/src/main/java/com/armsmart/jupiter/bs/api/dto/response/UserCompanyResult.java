package com.armsmart.jupiter.bs.api.dto.response;

import lombok.Data;
import org.apache.kafka.common.protocol.types.Field;

@Data
public class UserCompanyResult {
    /**
     *主键
     */
    private Integer id;

    private String companyName;
    /**
     *用户id
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户手机号
     */
    private String mobile;

    /**
     *企业id
     */
    private Integer companyId;

    /**
     *用户类型
     */
    private Integer userType;

    /**
     *是否企业官方指定用户
     */
    private Boolean isCompanyOfficial;

}
