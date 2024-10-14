package com.example.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto2{
    private Long id;
//    @JsonProperty(value = "roleName")
    private String roleName;
//    @JsonProperty(value = "roleKey")
    private String roleKey;
//    @JsonProperty(value = "roleSort")
    private Integer roleSort;
//    @JsonProperty(value = "status")
    private String status;
//    @JsonProperty(value = "menuIds")
    private List<String> menuIds;
//    @JsonProperty(value = "remark")
    private String remark;
}
