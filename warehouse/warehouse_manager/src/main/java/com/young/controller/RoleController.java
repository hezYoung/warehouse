package com.young.controller;

import com.young.dto.AssignAuthDto;
import com.young.page.Page;
import com.young.pojo.CurrentUser;
import com.young.pojo.Role;
import com.young.service.RoleService;
import com.young.utils.TokenUtils;
import com.young.vo.Result;
import com.young.vo.WarehouseConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/role")
@RestController
public class RoleController {

    //注入RoleService
    @Autowired
    private RoleService roleService;
    @Autowired
    private TokenUtils tokenUtils;

    /**
     * 查询所有角色的url接口role/role-list
     */
    @RequestMapping("/role-list")
    public Result queryAllRole() {
        //执行业务
        List<Role> roleList = roleService.getAllRole();
        //响应
        return Result.ok(roleList);
    }

    /**
     * 分页查询角色的url接口/role/role-page-list
     * <p>
     * 参数Page对象用于接收请求参数页码pageNum、每页行数pageSize;
     * 参数Role对象用于接收请求参数角色名roleName、角色代码roleCode、角色状态roleState;
     * <p>
     * 返回值Result对象向客户端响应组装了所有分页信息的Page对象;
     */
    @RequestMapping("/role-page-list")
    public Result rolepage(Page page, Role role) {
        Page rolePage = roleService.queryRolePage(page, role);
        return Result.ok("查询成功", rolePage);
    }

    /**
     * 添加角色的url接口/role/role-add
     *
     * @RequestBody Role role将添加的角色信息的json串数据封装到参数Role对象;
     * @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token
     * 将请求头Token的值即客户端归还的token赋值给参数变量token;
     */
    @RequestMapping("/role-add")
    public Result addRole(@RequestBody Role role,
                          @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即创建新角色的用户id
        int createBy = currentUser.getUserId();
        role.setCreateBy(createBy);

        //执行业务
        Result result = roleService.insertRole(role);
        return result;
    }

    //修改角色状态
    @RequestMapping("/role-state-update")
    public Result updateRoleState(@RequestBody Role role,
                                  @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token) {

//获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id,即修改角色的用户id
        int updateBy = currentUser.getUserId();

        //设置修改角色的用户id和修改时间
        role.setUpdateBy(updateBy);
        role.setUpdateTime(new Date());

        //执行业务
        Result result = roleService.updateRoleState(role);

        //响应
        return result;
    }

    //删除
    @RequestMapping("/role-delete/{roleId}")
    public Result deleterole(@PathVariable Integer roleId) {
        Result result = roleService.deleteRolebyId(roleId);
        return result;
    }

    /*自身已有的权限*/
    @RequestMapping("/role-auth")
    public Result queryRoleAuth(Integer roleId) {
        //执行业务
        List<Integer> authIdList = roleService.findallIds(roleId);
        //响应
        return Result.ok(authIdList);
    }

    /*添加权限的接口*/
    @RequestMapping("/auth-grant")
    public Result authgrant(@RequestBody AssignAuthDto assignAuthDto) {
        roleService.insertAuth(assignAuthDto);
        return Result.ok("权限分配成功");
    }
    /*角色完善*/
    @RequestMapping("/role-update")
    public Result updateRole(@RequestBody Role role,
                             @RequestHeader(WarehouseConstants.HEADER_TOKEN_NAME) String token){
        //获取当前登录的用户
        CurrentUser currentUser = tokenUtils.getCurrentUser(token);
        //获取当前登录的用户id -- 修改角色的用户id
        int updateBy = currentUser.getUserId();

        role.setUpdateBy(updateBy);

        //执行业务
        Result result = roleService.updateRoleDesc(role);

        //响应
        return result;
    }

}
