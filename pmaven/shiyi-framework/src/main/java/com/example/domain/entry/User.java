package com.example.domain.entry;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 用户表(User)表实体类
 *
 * @author makejava
 * @since 2024-08-03 15:14:34
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_user")
public class User  {
    @TableId
    private Integer id;

//用户名
    private String userName;
//昵称
    private String nickName;
//密码
    private String password;
//用户类型：0代表普通用户，1代表管理员
    private String type;
//账号状态（0正常 1停用）
    private String status;
//邮箱
    private String email;
//手机号
    private String phonenumber;
//用户性别（0男，1女，2未知）
    private String sex;
//头像
    private String avatar;
//创建人的用户id
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
//创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
//更新人
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
//更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
//删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}


