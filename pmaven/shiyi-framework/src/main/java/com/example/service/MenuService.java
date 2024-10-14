package com.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.domain.ResponseResult;
import com.example.domain.entry.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2024-08-08 20:27:17
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Integer id);

    List<Menu> selectRouterMenuTreeByUserId(Integer userId);

    ResponseResult menuList(String menuName, String status);


    ResponseResult addMenu(Menu menu);

    ResponseResult MenuById(Long id);

    ResponseResult updateMenu(Menu menu);

    ResponseResult treeSelect();

    ResponseResult roleMenuTreeselectById(Long id);
}


