package com.ssafy.pjt.model.service;

import com.ssafy.pjt.model.dto.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    User login(User user) throws Exception;

    int regist(User user) throws Exception;

    int modify(User user) throws Exception;

    int delete(User user) throws Exception;

    User detail(User user) throws Exception;

    List<User> getList(Map<String, String> map) throws Exception;

    String getUserId(Map<String, String> map);

    int updatePassword(Map<String, String> map);

    String getUserId2(Map<String, String> map);
}
