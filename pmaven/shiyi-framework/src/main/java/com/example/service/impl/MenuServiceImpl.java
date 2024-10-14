package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.domain.ResponseResult;
import com.example.domain.entry.Menu;
import com.example.domain.entry.RoleMenu;
import com.example.domain.vo.MenuTreeVo;
import com.example.domain.vo.MenuVo;
import com.example.domain.vo.RoleMenuTreeVo;
import com.example.mapper.MenuMapper;
import com.example.service.MenuService;
import com.example.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;


/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2024-08-08 20:27:17
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    private final RoleMenuServiceImpl roleMenuService;

    public MenuServiceImpl(RoleMenuServiceImpl roleMenuService) {
        this.roleMenuService = roleMenuService;
    }

    @Override
    public List<String> selectPermsByUserId(Integer id) {
        if(id == 1){
            LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
            queryWrapper.in(Menu::getMenuType,SystemConstants.MENU,SystemConstants.BUTTON);
            queryWrapper.eq(Menu::getStatus,SystemConstants.STATUS_NORMAL);
            List<Menu> list = list(queryWrapper);
            List<String> collect = list.stream().map(Menu::getPerms).collect(Collectors.toList());
            return collect;
        }
        return getBaseMapper().selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Integer userId) {
        MenuMapper menuMapper = getBaseMapper();
        List<Menu> menus = null;
        if(userId == 1){
            menus = menuMapper.selectAllRouterMenu();
        }else {
            menus = menuMapper.selectRouterMenuTreeByUserId(userId);
        }
        List<Menu> menuTree = builderMenuTree(menus,0L);
        return menuTree;
    }

    @Override
    public ResponseResult menuList(String menuName, String status) {
        LambdaQueryWrapper<Menu> queryWrapper = new LambdaQueryWrapper<Menu>();
        queryWrapper.eq(Objects.nonNull(menuName),Menu::getMenuName,menuName);
        queryWrapper.eq(Objects.nonNull(status),Menu::getStatus,status);
        queryWrapper.orderByAsc(Menu::getParentId,Menu::getOrderNum);
        List<Menu> list = list(queryWrapper);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(list, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    @Override
    public ResponseResult addMenu(Menu menu) {
        save(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult MenuById(Long id) {
        Menu menu = getById(id);
        MenuVo menuVo = BeanCopyUtils.copyBean(menu, MenuVo.class);
        return ResponseResult.okResult(menuVo);
    }

    @Override
    public ResponseResult updateMenu(Menu menu) {
        if(menu.getId().equals(menu.getParentId())){
            return ResponseResult.errorResult(500,"修改菜单'"+menu.getMenuName()+"‘失败，上级菜单不能选择自己");
        }
        updateById(menu);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        List<MenuTreeVo> collect = list()
                .stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null))
                .toList();
        List<MenuTreeVo> menuTreeVos = builderMenuTree2(collect,0L);
        return ResponseResult.okResult(menuTreeVos);
    }

    @Override
    public ResponseResult roleMenuTreeselectById(Long id) {
        RoleMenuTreeVo roleMenuTreeVo = new RoleMenuTreeVo();
        List<MenuTreeVo> collect = list()
                .stream()
                .map(menu -> new MenuTreeVo(menu.getId(), menu.getMenuName(), menu.getParentId(), null))
                .toList();
        List<MenuTreeVo> menuTreeVos = builderMenuTree2(collect,0L);
        roleMenuTreeVo.setMenus(menuTreeVos);
        LambdaQueryWrapper<RoleMenu> queryWrapper= new LambdaQueryWrapper<>();
        queryWrapper.eq(RoleMenu::getRoleId,id);
        List<RoleMenu> list = roleMenuService.list(queryWrapper);
        List<String> list1 = list.stream().map(roleMenu -> roleMenu.getMenuId().toString()).toList();
        roleMenuTreeVo.setCheckedKeys(list1);
        return ResponseResult.okResult(roleMenuTreeVo);
    }

    private List<MenuTreeVo> builderMenuTree2(List<MenuTreeVo> collect, long l) {
        List<MenuTreeVo> list = collect.stream().filter(m -> m.getParentId().equals(l)).map(m -> m.setChildren(getChildren2(collect,m))).toList();
        return list;
    }

    private List<MenuTreeVo> getChildren2(List<MenuTreeVo> collect, MenuTreeVo m) {
        List<MenuTreeVo> children = collect.stream().filter(m1->m1.getParentId().equals(m.getId())).map(m1->m1.setChildren(getChildren2(collect,m1))).toList();
        return children;
    }

    private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> collect = menus.stream().filter(menu -> menu.getParentId().equals(parentId)).map(menu -> menu.setChildren(getChildren(menu, menus))).collect(Collectors.toList());
        return collect;
    }

    /**
     * 获取子menu
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> collect = menus.stream().filter(menu1 -> menu1.getParentId().equals(menu.getId())).map(m -> m.setChildren(getChildren(m, menus))).collect(Collectors.toList());
        return collect;
    }
}


