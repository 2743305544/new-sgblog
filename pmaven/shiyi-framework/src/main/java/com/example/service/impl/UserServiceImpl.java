package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.dto.UserDto;
import com.example.domain.dto.UserDto2;
import com.example.domain.dto.UserDto3;
import com.example.domain.entry.Role;
import com.example.domain.entry.User;
import com.example.domain.entry.UserRole;
import com.example.domain.vo.PageVo;
import com.example.domain.vo.UserInfoVo;
import com.example.domain.vo.UserVo;
import com.example.domain.vo.UserVo2;
import com.example.enums.AppHttpCodeEnum;
import com.example.excption.SystemException;
import com.example.mapper.UserMapper;
import com.example.service.UserRoleService;
import com.example.service.UserService;
import com.example.utils.BeanCopyUtils;
import com.example.utils.SecurityUtils;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2024-08-04 15:49:36
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public ResponseResult userInfo(){
        Integer userId = SecurityUtils.getUserId();
        User user = getById(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseResult register(User user) {
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if(nickNameExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        String encodePassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePassword);
        saveOrUpdate(user);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult userList(Integer pageNum, Integer pageSize, Integer userName, String phonenumber, String status) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Objects.nonNull(userName),User::getUserName,userName);
        queryWrapper.eq(Objects.nonNull(phonenumber),User::getPhonenumber,phonenumber);
        queryWrapper.eq(Objects.nonNull(status),User::getStatus,status);
        Page<User> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);
        List<UserVo> userVos = BeanCopyUtils.copyBeanList(page.getRecords(), UserVo.class);
        return ResponseResult.okResult(new PageVo(userVos, page.getTotal()));
    }

    @Resource
    private UserRoleService userRoleService;

    @Override
    @Transactional
    public ResponseResult addUser(UserDto userDto) {
        if(Objects.isNull(userDto.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
        if(!StringUtils.hasText(userDto.getPhonenumber())){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_NOT_NULL);
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName,userDto.getUserName());
        if(getOne(queryWrapper) != null){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        LambdaQueryWrapper<User> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(User::getPhonenumber,userDto.getPhonenumber());
        if(getOne(queryWrapper1) != null){
            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_EXIST);
        }
        LambdaQueryWrapper<User> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(User::getEmail,userDto.getEmail());
        if(getOne(queryWrapper2) != null){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        save(user);

        Integer id = user.getId();
        List<UserRole> list = userDto.getRoleIds().stream().map(roleId -> new UserRole(id, Long.valueOf(roleId))).toList();
        userRoleService.saveBatch(list);
        return ResponseResult.okResult();

    }

    @Override
    @Transactional
    public ResponseResult getUserDetailById(Integer id) {
        UserVo2 userVo2 = new UserVo2();
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,id);
        List<String> roleIds = userRoleService.list(queryWrapper).stream().map(r -> r.getRoleId().toString()).toList();
        userVo2.setRoleIds(roleIds);
        LambdaQueryWrapper<Role> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Role::getStatus, SystemConstants.STATUS_NORMAL);
        List<Role> list = roleService.list(queryWrapper1);
        userVo2.setRoles(list);
        User user = getById(id);
        UserDto2 userDto2 = BeanCopyUtils.copyBean(user, UserDto2.class);
        userVo2.setUser(userDto2);
        return ResponseResult.okResult(userVo2);
    }

    @Override
    public ResponseResult updateUser(UserDto3 userDto) {
        User user = BeanCopyUtils.copyBean(userDto, User.class);
        updateById(user);
        LambdaQueryWrapper<UserRole> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserRole::getUserId,user.getId());
        userRoleService.remove(queryWrapper);
        List<UserRole> list = userDto.getRoleIds().stream().map(roleId -> new UserRole(user.getId(), Long.valueOf(roleId))).toList();
        userRoleService.saveBatch(list);
        return ResponseResult.okResult();
    }

    private boolean nickNameExist(String nickName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getNickName, nickName);
        return count(queryWrapper) > 0;
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>();
        queryWrapper.eq(User::getUserName, userName);
        return count(queryWrapper) > 0;
    }


}


