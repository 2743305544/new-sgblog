package com.example.domain.entry;

import java.util.Date;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 标签(Tag)表实体类
 *
 * @author makejava
 * @since 2024-08-08 15:13:28
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_tag")
public class Tag  {
    @TableId
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

//标签名
    private String name;

    private Long createBy;

    private Date createTime;

    @JsonSerialize(using = ToStringSerializer.class)
    private Long updateBy;

    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
//删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
//备注
    private String remark;



}


