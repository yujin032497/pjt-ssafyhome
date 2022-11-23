package com.ssafy.pjt;

import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.mapper.UserDao;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class UserDaoTest extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoTest.class);

    // 픽스처(fixture): 테스트를 수행하는데 필요한 정보나 객체
    @Autowired
    private UserDao userDao;


    private User user1;
    private User user2;
    // 픽스처 끝

    @Before
    public void setUp() {
        user1 = new User();
        user1.setId("ssafy1");
        user1.setPassword("1234");
        user1.setName("김싸피");
        user1.setAddress("서울시 강남구 역삼동");
        user1.setPhone("010-1111-2222");
        user1.setIsAdmin(0);
    }

    @Test
    // 테스트 OK
    public void loginTest() {
        User testUser = userDao.selectUser(user1);
        logger.debug("testUser: {}", testUser.toString());
        assertEquals(user1.getId(), testUser.getId());
        assertNull(testUser.getPassword());
        assertEquals(user1.getName(), testUser.getName());
        assertEquals(user1.getAddress(), testUser.getAddress());
        assertEquals(user1.getPhone(), testUser.getPhone());
        assertEquals(user1.getIsAdmin(), testUser.getIsAdmin());
    }

    @Test
    public void listTest() {
        Map<String, String> map = new HashMap<>();
        List<User> list = userDao.selectUserList(map);
        logger.debug(list.toString());
    }

//	@Test
//	// 테스트 OK
//	public void deleteAllTest() {
//		userDao.deleteAll();
//		assertEquals(0, userDao.selectCount());
//	}
}
