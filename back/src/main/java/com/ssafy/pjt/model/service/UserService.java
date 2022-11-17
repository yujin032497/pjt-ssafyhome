package com.ssafy.pjt.model.service;

import com.ssafy.pjt.model.dto.User;

import java.util.List;

public interface UserService {

    User login(User user) throws Exception;

    int regist(User user) throws Exception;

    int modify(User user) throws Exception;

    int delete(User user) throws Exception;

    User detail(User user) throws Exception;

    List<User> getList() throws Exception;
}
