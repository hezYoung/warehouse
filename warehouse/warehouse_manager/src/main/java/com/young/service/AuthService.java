package com.young.service;

import com.young.pojo.Auth;

import java.util.List;

public interface AuthService {
    List<Auth> findbyAuth(int userId);

    List<Auth> findAuthTree();
}
