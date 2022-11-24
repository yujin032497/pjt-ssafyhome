package com.ssafy.pjt.model.mapper;

import com.ssafy.pjt.model.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface UserDao {
    public User selectUser(User user);

    public User selectUserInfo(String id);

    public int insertUser(User user);

    public int updateUser(User user);

    public int deleteUser(String userId);

    public void deleteAll();

    public int insertUserFavorite(String userId, String dongCode);

    public List<User> selectUserList(Map<String, String> id);

    public int selectCount();

    String selectUserId(Map<String, String> map);

    int updatePassword(Map<String, String> map);

    String selectUserId2(Map<String, String> map);
}
