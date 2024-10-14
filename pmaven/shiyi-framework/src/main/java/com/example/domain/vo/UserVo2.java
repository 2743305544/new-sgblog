package com.example.domain.vo;

import com.example.domain.dto.UserDto2;
import com.example.domain.entry.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo2 {
    List<String> roleIds;
    List<Role> roles;
    UserDto2 user;
}
