package org.admin.controller;

import com.example.domain.ResponseResult;
import com.example.domain.entry.LoginUser;
import com.example.domain.entry.Menu;
import com.example.domain.entry.User;
import com.example.domain.vo.AdminUserInfoVo;
import com.example.domain.vo.RoutersVo;
import com.example.domain.vo.UserInfoVo;
import com.example.enums.AppHttpCodeEnum;
import com.example.excption.SystemException;
import com.example.service.LoginService;
import com.example.service.RoleService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.RedisCache;
import com.example.utils.SecurityUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.service.MenuService;

import java.util.List;


@RestController
public class LoginController {
    @Resource
    private LoginService loginService;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;


    @PostMapping("/user/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return loginService.login(user);
    }

    @GetMapping("/user/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo(){
        LoginUser loginUser = SecurityUtils.getLoginUser();
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        List<String> roles = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms,roles, BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class));
        return ResponseResult.okResult(adminUserInfoVo);
    }

    @GetMapping("getRouters")
    public ResponseResult<RoutersVo> getInfo1(){
        Integer userId = SecurityUtils.getUserId();
        List<Menu> menus = menuService.selectRouterMenuTreeByUserId(userId);
        return ResponseResult.okResult(new RoutersVo(menus));
    }

    @PostMapping("/user/logout")
    public ResponseResult logout(){
        return loginService.logout();
    }
}
