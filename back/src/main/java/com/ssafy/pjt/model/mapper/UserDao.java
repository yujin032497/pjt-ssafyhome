package com.ssafy.pjt.model.mapper;

import com.ssafy.pjt.model.dto.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao {
    public User selectUser(User user);

    public User selectUserInfo(String id);

    public int insertUser(User user);

    public int updateUser(User user);

    public int deleteUser(String userId);

    public void deleteAll();

    public int insertUserFavorite(String userId, String dongCode);

    public List<User> selectUserList();

    public int selectCount();
}
