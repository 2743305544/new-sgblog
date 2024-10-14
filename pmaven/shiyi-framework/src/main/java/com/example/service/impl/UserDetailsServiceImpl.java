package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.constants.SystemConstants;
import com.example.domain.entry.LoginUser;
import com.example.domain.entry.User;
import com.example.mapper.MenuMapper;
import com.example.mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Resource
    private MenuMapper menuMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        if(Objects.isNull(user)) {
            throw new RuntimeException("用户不存在");
        }
        //TODO 查询权限
        if(user.getType().equals(SystemConstants.ADMIN)) {
            List<String> strings = menuMapper.selectPermsByUserId(user.getId());
            return new LoginUser(user,strings);
        }
        return new LoginUser(user,null);
    }
}
