package com.armsmart.jupiter.bs.api.dto.request;

import lombok.Data;

@Data
public class UserResetPwdParam {

    private String mobile;

    private String password;

    private String verifyCode;

}
