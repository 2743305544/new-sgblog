package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.UserDto;
import com.example.domain.dto.UserDto3;
import com.example.domain.entry.User;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2024-08-04 15:49:36
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult userList(Integer pageNum, Integer pageSize, Integer userName, String phonenumber, String status);

    ResponseResult addUser(UserDto userDto);

    ResponseResult getUserDetailById(Integer id);

    ResponseResult updateUser(UserDto3 userDto);
}


