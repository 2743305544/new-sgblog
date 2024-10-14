package com.example.domain.entry;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 分类表(Category)表实体类
 *
 * @author makejava
 * @since 2024-08-02 15:28:09
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_category")
public class Category  {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

//分类名
    private String name;
//父分类id，如果没有父分类为-1
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;
//描述
    private String description;
//状态0:正常,1禁用
    private String status;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT)
    private Integer createBy;

    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Integer updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
//删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;



}


