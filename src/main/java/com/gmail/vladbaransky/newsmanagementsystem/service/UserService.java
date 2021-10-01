package com.gmail.vladbaransky.newsmanagementsystem.service;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.User;

public interface UserService {
    User getUserByLogin(String login);
}
