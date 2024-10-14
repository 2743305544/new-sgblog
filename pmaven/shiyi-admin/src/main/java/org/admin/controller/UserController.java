package org.admin.controller;

import com.example.domain.ResponseResult;
import com.example.domain.dto.UserDto;
import com.example.domain.dto.UserDto3;
import com.example.domain.entry.User;
import com.example.service.UserRoleService;
import com.example.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private UserRoleService userRoleService;

    @GetMapping("/getInfo/list")
    public ResponseResult userList(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam(value = "userName",required = false) Integer userName,
                                   @RequestParam(value = "phonenumber",required = false) String phonenumber,
                                   @RequestParam(value = "status",required = false) String status){
        return userService.userList(pageNum,pageSize,userName,phonenumber,status);
    }
    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto){
        return userService.addUser(userDto);
    }
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseResult deleteUser(@PathVariable("id") Integer id){
        userService.removeById(id);
        userRoleService.removeById(id);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getUserDetailById(@PathVariable("id") Integer id){
        return userService.getUserDetailById(id);
    }
    @PutMapping
    public ResponseResult updateUser(@RequestBody UserDto3 userDto){
        return userService.updateUser(userDto);
    }

}
