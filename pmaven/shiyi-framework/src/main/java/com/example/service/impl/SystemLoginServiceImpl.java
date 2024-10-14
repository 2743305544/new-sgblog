package com.example.service.impl;

import com.example.domain.ResponseResult;
import com.example.domain.entry.LoginUser;
import com.example.domain.entry.User;
import com.example.domain.vo.BlogUserLoginVo;
import com.example.domain.vo.UserInfoVo;
import com.example.service.LoginService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import com.example.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Component
public class SystemLoginServiceImpl implements LoginService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("用户名或者密码错误");
        }
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        Integer id = loginUser.getUser().getId();
        String jwt = JwtUtil.createJWT(String.valueOf(id));
        redisCache.setCacheObject("login:"+id,loginUser);
        Map<String,Object> map = new HashMap<>();
        map.put("token",jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        Integer userId = SecurityUtils.getUserId();
        redisCache.deleteObject("login:"+userId);
        return ResponseResult.okResult();
    }
}
