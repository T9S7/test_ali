package com.armsmart.common.utils;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class PhoneNumValidationUtil {

    private static final Pattern PATTERN = Pattern.compile("^1[345678]\\d{9}$");

    /**
     * 验证手机号
     */
    public static boolean isPhone(String iphone) {
        if (!StringUtils.hasText(iphone)) {
            return false;
        } else {
            return PATTERN.matcher(iphone).matches();
        }
    }

}
