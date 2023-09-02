package com.young.pojo;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 用户角色表
 * @TableName user_role
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRole implements Serializable {
    /**
     * 
     */

    private Integer userRoleId;

    /**
     * 
     */
    private Integer roleId;

    /**
     * 
     */
    private Integer userId;


    private static final long serialVersionUID = 1L;
}