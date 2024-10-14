package org.admin.controller;


import com.example.domain.ResponseResult;
import com.example.domain.dto.ChangeRoleStatusDto;
import com.example.domain.dto.RoleDto;
import com.example.domain.dto.RoleDto2;
import com.example.domain.entry.Role;
import com.example.service.RoleService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult roleList(@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize,@RequestParam(value = "roleName",required = false) String roleName,@RequestParam(value = "status",required = false) String status) {
        return roleService.roleList(pageNum,pageSize,roleName,status);
    }
    @PutMapping("/changeStatus")
    public ResponseResult changeStatus(@RequestBody ChangeRoleStatusDto changeRoleStatusDto) {
        return roleService.changeStatus(changeRoleStatusDto);
    }
    @PostMapping
    public ResponseResult addRole(@RequestBody RoleDto role) {
        return roleService.addRole(role);
    }
    @GetMapping("/{id}")
    public ResponseResult roleDetail(@PathVariable("id") Long id) {
        return roleService.roleDetail(id);
    }
    @PutMapping
    public ResponseResult updateRole(@RequestBody RoleDto2 role) {
        return roleService.updateRole(role);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteRoleById(@PathVariable("id") Long id) {
        roleService.removeById(id);
        return ResponseResult.okResult();
    }
    @GetMapping("/listAllRole")
    public ResponseResult listAllRole() {
        return roleService.listAllRole();
    }
}
