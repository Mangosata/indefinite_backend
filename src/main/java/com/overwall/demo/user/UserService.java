package com.overwall.demo.user;

import com.overwall.demo.user.status.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserService")
public class UserService {

    @Autowired
    UserRepository userRepository;

    public User register(User user) {
        User foundUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (foundUser == null) {
            User newUser = new User();
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
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
