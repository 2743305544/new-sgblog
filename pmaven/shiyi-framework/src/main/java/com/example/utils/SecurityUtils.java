package com.example.utils;

import com.example.domain.entry.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author 35238
 * @date 2023/7/26 0026 20:43
 */

public class SecurityUtils {

    /**
     * 获取用户的userid
     **/
    public static LoginUser getLoginUser() {
        return (LoginUser) getAuthentication().getPrincipal();
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 指定userid为1的用户就是网站管理员
     * @return
     */
    public static Boolean isAdmin(){
        Integer id = getLoginUser().getUser().getId();
        return id != null && 1 == id;
    }

    public static Integer getUserId() {
        return getLoginUser().getUser().getId();
    }
}
