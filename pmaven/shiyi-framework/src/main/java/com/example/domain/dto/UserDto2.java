package com.example.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto2 {
    //用户名
    private String userName;
    //昵称
    private String nickName;
    //密码
    private String password;

    //账号状态（0正常 1停用）
    private String status;
    //邮箱
    private String email;
    //用户性别（0男，1女，2未知）
    private String sex;

}

