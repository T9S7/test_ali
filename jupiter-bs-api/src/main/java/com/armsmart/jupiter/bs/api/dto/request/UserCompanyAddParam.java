package com.armsmart.jupiter.bs.api.dto.request;

import lombok.Data;

@Data
public class UserCompanyAddParam {
    /**
     *用户id
     */
    private Integer userId;

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
