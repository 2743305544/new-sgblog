package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.dto.ChangeRoleStatusDto;
import com.example.domain.dto.RoleDto;
import com.example.domain.dto.RoleDto2;
import com.example.domain.entry.Role;
import com.example.domain.entry.RoleMenu;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.RoleVo;
import com.example.mapper.RoleMapper;
import com.example.service.RoleMenuService;
import com.example.service.RoleService;
import com.example.utils.BeanCopyUtils;
import io.netty.util.collection.LongObjectHashMap;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2024-08-08 20:40:19
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Integer id) {
        if(id == 1){
            List<String> roleKeys = new ArrayList<String>();
            roleKeys.add("admin");
            return roleKeys;
        }
        return getBaseMapper().selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult roleList(Integer pageNum, Integer pageSize, String roleName, String status) {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.like(Objects.nonNull(roleName),Role::getRoleName, roleName);
        lqw.eq(Objects.nonNull(status),Role::getStatus,status);
        lqw.orderByAsc(Role::getRoleSort);
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, lqw);
        return ResponseResult.okResult(new PageVo(page.getRecords(),page.getTotal()));
    }

    @Resource
    RoleMenuService roleMenuService;

    @Override
    public ResponseResult changeStatus(ChangeRoleStatusDto changeRoleStatusDto) {
        Role role = getById(changeRoleStatusDto.getRoleId());
        role.setStatus(changeRoleStatusDto.getStatus());
        updateById(role);
        return ResponseResult.okResult();
    }

    @Resource
    RoleMapper roleMapper;

    @Override
    @Transactional
    public ResponseResult addRole(RoleDto role) {
        Role role1 = BeanCopyUtils.copyBean(role, Role.class);
//        System.out.println("111111111111111111111111111111111111111111111"+role1);
        save(role1);
        Long roleId = role1.getId();
        List<RoleMenu> list = role.getMenuIds().stream().map(m -> new RoleMenu(roleId, Long.valueOf(m))).toList();
        roleMenuService.saveBatch(list);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult roleDetail(Long id) {
        Role role = getById(id);
        return ResponseResult.okResult(BeanCopyUtils.copyBean(role, RoleVo.class));
    }

    @Override
    public ResponseResult updateRole(RoleDto2 role) {
        Role role1 = BeanCopyUtils.copyBean(role, Role.class);
        updateById(role1);
        LambdaQueryWrapper<RoleMenu> lqw = new LambdaQueryWrapper<>();
        lqw.eq(RoleMenu::getRoleId, role1.getId());
        roleMenuService.remove(lqw);
        List<RoleMenu> list = role.getMenuIds().stream().map(m -> new RoleMenu(role1.getId(), Long.valueOf(m))).toList();
        roleMenuService.saveBatch(list);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult listAllRole() {
        LambdaQueryWrapper<Role> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = list(lqw);
        return ResponseResult.okResult(list);
    }


}


