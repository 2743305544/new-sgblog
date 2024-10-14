package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entry.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-08 20:26:50
 */
public interface MenuMapper extends BaseMapper<Menu> {

    @Select("select distinct m.perms from sys_user_role ur left join sys_role_menu rm ON ur.role_id = rm.role_id left join sys_menu m ON m.id = rm.menu_id where ur.user_id = #{id} AND m.menu_type in ('C','F') AND m.status = 0 and m.del_flag = 0")
    List<String> selectPermsByUserId(Integer id);

    @Select("select DISTINCT m.id, m.parent_id, m.menu_name, m.path, m.component, m.visible, m.status, IFNULL(m.perms,'') AS perms, m.is_frame,  m.menu_type, m.icon, m.order_num, m.create_time  from   sys_menu m  where  m.menu_type in ('C','M') AND m.status = 0 and m.del_flag = 0 order by m.parent_id,m.order_num")
    List<Menu> selectAllRouterMenu();
    @Select("select DISTINCT m.id, m.parent_id, m.menu_name, m.path, m.component, m.visible, m.status, IFNULL(m.perms,'') AS perms, m.is_frame,  m.menu_type, m.icon, m.order_num, m.create_time  from sys_user_role ur left join sys_role_menu rm ON ur.role_id = rm.role_id left join sys_menu m ON m.id = rm.menu_id where  m.menu_type in ('C','M') AND m.status = 0 and m.del_flag = 0 and ur.user_id = #{userid} order by m.parent_id,m.order_num")
    List<Menu> selectRouterMenuTreeByUserId(Integer userId);
}


