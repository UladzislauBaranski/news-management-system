package com.gmail.vladbaransky.newsmanagementsystem.service.impl;

import com.gmail.vladbaransky.newsmanagementsystem.repository.UserRepository;
import com.gmail.vladbaransky.newsmanagementsystem.repository.model.User;
import com.gmail.vladbaransky.newsmanagementsystem.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.lang.invoke.MethodHandles;

@Component
public class UserServiceImpl implements UserService {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByLogin(String login) {
        User userByLogin = userRepository.getUserByLogin(login);
        if (userByLogin != null) {
            return userByLogin;
        } else {
            logger.info("User with login: " + login + " not found");
            return null;
        }
    }
}
