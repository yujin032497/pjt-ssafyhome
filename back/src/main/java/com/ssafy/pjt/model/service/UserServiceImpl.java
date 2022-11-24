package com.ssafy.pjt.model.service;

import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.mapper.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;

    @Override
    public User login(User user) throws Exception {
        return dao.selectUser(user);
    }

    @Override
    public int regist(User user) throws Exception {
        // TODO Auto-generated method stub
        return dao.insertUser(user);
    }

    @Override
    public int modify(User user) {
        // TODO Auto-generated method stub
        return dao.updateUser(user);
    }

    @Override
    public int delete(User user) throws Exception {
        // TODO Auto-generated method stub
        return dao.deleteUser(user.getId());
    }

    @Override
    public User detail(User user) throws Exception {
        return dao.selectUserInfo(user.getId());
    }

    @Override
    public List<User> getList(Map<String, String> map) throws Exception {
        if(map.get("key").equals("undefined")){
            map.put("key", null);
        }
        return dao.selectUserList(map);
    }

    @Override
    public String getUserId(Map<String, String> map) {
        return dao.selectUserId(map);
    }
    @Override
    public String getUserId2(Map<String, String> map) {
        return dao.selectUserId2(map);
    }

    @Override
    public int updatePassword(Map<String, String> map) {
        return dao.updatePassword(map);
    }

}
