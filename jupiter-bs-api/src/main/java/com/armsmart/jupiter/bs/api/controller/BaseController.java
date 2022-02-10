package com.armsmart.jupiter.bs.api.controller;

import com.armsmart.jupiter.bs.api.security.AppUserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author wei.lin
 * @date 2021/12/14
 */
@Slf4j
public class BaseController {

    protected Integer getCurrentUserId() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if ("anonymousUser".equals(principal)) {
                return null;
            } else {
                AppUserDetails appUserDetails = (AppUserDetails) principal;
                return appUserDetails.getUserInfo().getId();
            }
        } catch (Exception e) {
            log.warn("获取用户ID失败：", e);
        }
        return null;
    }
}
