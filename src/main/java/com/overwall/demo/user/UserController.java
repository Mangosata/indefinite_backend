package com.overwall.demo.user;

import com.overwall.demo.core.CoreResponseBody;
import com.overwall.demo.user.User;
import com.overwall.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/hello")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello world");
    }

    //login
//    @PostMapping("/login")
//    public String login(@RequestParam String username,
//                        @RequestParam String password,
//                        Map<String, Object> map,
//                        HttpSession session) {
//        if (!StringUtils.isEmpty(username) && "123456".equals(password)) {
//            //防止表单重复提交，重定向到主页
//            session.setAttribute("loginUser", username);
//            return "redirect:/main.html";
//        } else {
//            map.put("msg", "用户名密码错误");
//            return "mylogin";
//        }
//    }

    @PostMapping("/login")
    public ResponseEntity<CoreResponseBody> login(@RequestBody User user) {
        CoreResponseBody res = userService.isUserOrPassword(user);
        // 密码和用户名不能为空
        if (res != null) {
            return ResponseEntity.ok(res);
        }

        String loginToken = userService.login(user);
        if (loginToken == null) {
            res = new CoreResponseBody(null, "Username or password is wrong", null);
        } else {
            // 给前端返回token
            res = new CoreResponseBody(loginToken, "Get Token", null);
        }
        return ResponseEntity.ok(res);
    }


    //register
    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CoreResponseBody> register(@RequestBody User user) {
        // 输出格式
        CoreResponseBody res = userService.isUserOrPassword(user);
        // 密码和用户名不能为空
        if (res != null) {
            return ResponseEntity.ok(res);
        }

        User savedUser = userService.register(user);
        if (savedUser == null) {
            res = new CoreResponseBody(null, "User Existed", new Exception("User Existed"));
        } else {
            res = new CoreResponseBody(savedUser, "User Created Successfully", null);
        }
        return ResponseEntity.ok(res);
    }

    //list all
    @GetMapping("/users")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CoreResponseBody> listAll() {
        CoreResponseBody res;
        List userList = userService.listAll();

        res = new CoreResponseBody(userList, "user list", null);
        return ResponseEntity.ok(res);
    }

    //find one
    @GetMapping("/users/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CoreResponseBody> findById(@PathVariable Long id) {
        CoreResponseBody res;
        User foundUser = userService.findById(id);
        if (foundUser == null) {
            res = new CoreResponseBody(null, "Can not find this user", new Exception("Can not find this user"));
        } else {
            res = new CoreResponseBody(foundUser, "found this user by id", null);
        }
        return ResponseEntity.ok(res);
    }

    //delete
    @DeleteMapping("/users/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CoreResponseBody> deleteById(@PathVariable Long id) {
        CoreResponseBody res;
        User foundUser = userService.findById(id);
        if (foundUser == null) {
            res = new CoreResponseBody(null, "Can not find this user", new Exception("Can not find this user"));
        } else {
            userService.deleteById(id);
            res = new CoreResponseBody(foundUser, "deleted!", null);
        }
        return ResponseEntity.ok(res);
    }

    //update
    @PutMapping("/users/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<CoreResponseBody> updateById(@RequestBody User user, @PathVariable Long id) {
        CoreResponseBody res;
        User savedUser = userService.updateById(user, id);

        if (savedUser == null) {
            res = new CoreResponseBody(null, "Can not find this user", new Exception("Can not find this user"));
        } else {
            res = new CoreResponseBody(savedUser, "Update Successfully", null);
        }
        return ResponseEntity.ok(res);
    }

}
