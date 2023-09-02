package com.young.mapper;

import com.young.pojo.Auth;

import java.util.List;

public interface AuthMapper {
    List<Auth> findbyAuth(int userId);

    List<Auth> findAuthTree();


}