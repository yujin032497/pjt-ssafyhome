package com.ssafy.pjt.controller;

import com.ssafy.pjt.model.dto.User;
import com.ssafy.pjt.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RestController
@RequestMapping("/api/user")
@CrossOrigin("*")
public class UserRestController {
    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        try {
            User loginUser = userService.login(user);
            if (loginUser != null) {
                //session.setAttribute("loginUser", loginUser);
                return new ResponseEntity<>(loginUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        try {
            session.invalidate();
            return new ResponseEntity<Void>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/regist")
    public ResponseEntity<?> regist(@RequestBody User user) {
        logger.debug("user: {}", user.toString());
        // 기본 관리자 권한은 없음.
        user.setIsAdmin(0);
        try {
            userService.regist(user);
            return new ResponseEntity<Void>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody User user, HttpSession session) {
        try {
            if (user != null) {
                userService.modify(user);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        try {
            if (loginUser != null) {
                userService.delete(loginUser);
                session.invalidate();
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteAdmin(@PathVariable("userId") String userId) {
        User user = new User();
        user.setId(userId);
        try {
            if (user != null) {
                userService.delete(user);
                return new ResponseEntity<Void>(HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail")
    public ResponseEntity<?> detail(HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        try {
            if (loginUser != null) {
                loginUser = userService.detail(loginUser);
                return new ResponseEntity<>(loginUser, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/detail/{userId}")
    public ResponseEntity<?> detailAdmin(@PathVariable("userId") String userId) {
        User user = new User();
        user.setId(userId);
        try {
            if (user != null) {
                user = userService.detail(user);
                return new ResponseEntity<>(user, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<?> getUserList() {
        try {
            List<User> userList = userService.getList();
            if (userList != null && userList.size() > 0) {
                return new ResponseEntity<>(userList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            return new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
