package com.example.domain.entry;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;

/**
 * 角色信息表(Role)表实体类
 *
 * @author makejava
 * @since 2024-08-08 20:40:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sys_role")
@Accessors(chain = true)
public class Role  {
//角色ID
    @TableId(type = IdType.AUTO)
    private Long id;
//角色名称
    private String roleName;
//角色权限字符串
    private String roleKey;
//显示顺序
    private Integer roleSort;
//角色状态（0正常 1停用）
    private String status;
//删除标志（0代表存在 1代表删除）
    private String delFlag;
//创建者
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;
//创建时间
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
//更新者
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;
//更新时间
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
//备注
    private String remark;



}


