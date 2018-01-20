package com.glaum.login.service;

import com.glaum.login.model.User;
import com.glaum.login.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        if (isUserExist(user.getUsername())) {
            return false;
        }
        encodePassword(user);
        return userDao.createUser(user) != 0;
    }

    public boolean createAdmin(User user) {
        if (isUserExist(user.getUsername())) {
            return false;
        }
        encodePassword(user);
        return userDao.creatAdmin(user) != 0;
    }

    private void encodePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
    }

    private  boolean isUserExist(String username) {
        return userDao.findUserByUsername(username) != null;
    }

}
