package com.overwall.demo.user;

import com.overwall.demo.core.CoreResponseBody;
import com.overwall.demo.user.status.UserStatus;
import com.overwall.demo.user.token.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;


@Service("UserService")
public class UserService {

    @Autowired
    UserRepository userRepository;

    Token userToken = new Token();

    private PasswordEncoder encoder;

    @PostConstruct
    protected void init() {
        encoder = new BCryptPasswordEncoder();
    }

    public CoreResponseBody isUserOrPassword (User user) {
        CoreResponseBody res = null;
        //用户名和密码必须存在
        if (user.getUsername() == null || user.getPassword() == null) {
            res = new CoreResponseBody(null, "", new Exception("Parameter is null"));
            return res;
        }
        //用户名密码不能为空字符
        if (user.getUsername().isEmpty() || user.getPassword().isEmpty()) {
            res = new CoreResponseBody(null, "", new Exception("Username or Password is empty"));
            return res;
        }
        return null;
    }

    public String login(User user) {
        User loginUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (loginUser != null) {
            // 密码是否匹配
            return encoder.matches(user.getPassword(), loginUser.getPassword()) ?
                    userToken.createToken(user) : null;
        }else {
            // 用户名不匹配
            return null;
        }
    }

    public User register(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (foundUser == null) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(encoder.encode(user.getPassword()));
            newUser.setStatus(UserStatus.Active);

            User savedUser = userRepository.save(newUser);
            return savedUser;
        } else {
            return null;
        }
    }

    public List listAll() {
        List userList = userRepository.findAll();
        return userList;
    }

    public User findById(Long id) {
        User foundUser = userRepository.findById(id).orElse(null);
        return foundUser;
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateById(User user, Long id) {
        User foundUser = userRepository.findById(id).orElse(null);
        if (foundUser == null) {
            return null;
        } else {
            foundUser.setUsername(user.getUsername());
            foundUser.setPassword(user.getPassword());
            User savedUser = userRepository.save(foundUser);
            return savedUser;
        }
    }

}
