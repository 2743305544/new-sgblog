package com.example.service;

import com.example.domain.ResponseResult;
import com.example.domain.entry.User;

public interface BlogLoginService {

    ResponseResult login(User user);

    ResponseResult logout();
}
