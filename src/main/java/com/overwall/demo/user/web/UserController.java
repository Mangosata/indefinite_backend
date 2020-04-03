package com.overwall.demo.user.web;

import com.overwall.demo.core.CoreResponseBody;
import com.overwall.demo.user.User;
import com.overwall.demo.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    //register
    @PostMapping("/users")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<CoreResponseBody> register(@RequestBody User user) {
        CoreResponseBody res = null;

        if (user.getUsername() == null || user.getPassword() == null) {
            res = new CoreResponseBody(null, "", new Exception("Parameter is null"));
            return ResponseEntity.ok(res);
        }

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            res = new CoreResponseBody(null, "", new Exception("Username or Password is empty"));
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
