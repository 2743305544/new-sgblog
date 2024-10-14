package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.dto.ChangeRoleStatusDto;
import com.example.domain.dto.RoleDto;
import com.example.domain.dto.RoleDto2;
import com.example.domain.entry.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2024-08-08 20:40:19
 */
public interface RoleService extends IService<Role> {
    List<String> selectRoleKeyByUserId(Integer id);

    ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status);

    ResponseResult changeStatus(ChangeRoleStatusDto changeRoleStatusDto);

    ResponseResult addRole(RoleDto role);

    ResponseResult roleDetail(Long id);

    ResponseResult updateRole(RoleDto2 role);

    ResponseResult listAllRole();
}


