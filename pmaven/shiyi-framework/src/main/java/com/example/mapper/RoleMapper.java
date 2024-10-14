package com.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.domain.entry.Role;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2024-08-08 20:40:19
 */
public interface RoleMapper extends BaseMapper<Role> {

    @Select("select r.role_key from sg_blog.sys_user_role ur left join sys_role r on ur.role_id = r.id where ur.user_id = #{id} and r.status = 0 and r.del_flag = 0")
    List<String> selectRoleKeyByUserId(Integer id);

//    @Insert("insert into sys_role(role_name,role_key,role_sort,status,remark)")
}


