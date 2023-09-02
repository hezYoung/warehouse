package com.young.pojo;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @TableName role
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Role implements Serializable {
    private Integer roleId;

    private String roleName;

    private String roleDesc;

    private String roleCode;

    private String roleState;

    private Integer createBy;
    //json转换的日期格式
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
    private String getCode;
}