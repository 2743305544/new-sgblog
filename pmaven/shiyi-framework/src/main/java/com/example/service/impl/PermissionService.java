package com.example.service.impl;

import com.example.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("ps")
public class PermissionService {
    public boolean hasPermission(String permission) {
        if(SecurityUtils.isAdmin()){
            return true;
        }
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
