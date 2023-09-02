package com.young.controller;

import com.young.pojo.Auth;
import com.young.service.AuthService;
import com.young.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    //查询出权限
    @RequestMapping("/auth-tree")
    public Result authtree() {
        List<Auth> authTree = authService.findAuthTree();
        return Result.ok(authTree);
    }
}
