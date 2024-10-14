package com.example.service;

import com.example.domain.ResponseResult;
import com.example.domain.entry.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
