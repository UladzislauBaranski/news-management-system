package com.gmail.vladbaransky.newsmanagementsystem.repository;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User getUserByLogin(String login);
}
