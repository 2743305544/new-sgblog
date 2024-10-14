package com.example.service.impl;

import com.example.domain.ResponseResult;
import com.example.domain.entry.LoginUser;
import com.example.domain.entry.User;
import com.example.domain.vo.BlogUserLoginVo;
import com.example.domain.vo.UserInfoVo;
import com.example.service.BlogLoginService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.JwtUtil;
import com.example.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {

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
        redisCache.setCacheObject("bloglogin:"+id,loginUser);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        BlogUserLoginVo blogUserLoginVo = new BlogUserLoginVo(jwt, userInfoVo);
        return ResponseResult.okResult(blogUserLoginVo);
    }

    @Override
    public ResponseResult logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        Integer id = principal.getUser().getId();
        redisCache.deleteObject("bloglogin:"+id);
        return ResponseResult.okResult();
    }
}
