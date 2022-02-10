package com.armsmart.jupiter.bs.api.dto.request;

import lombok.Data;

@Data
public class UserModifyPwdParam {

    private String mobile;

    private String oldPassword;

    private String newPassword;

}
