package com.example.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenuTreeVo {
    private List<MenuTreeVo> menus;
    List<String> checkedKeys;
}
