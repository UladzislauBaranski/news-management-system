/*
package com.gmail.vladbaransky.newsmanagementsystem.service.impl;

import com.gmail.vladbaransky.newsmanagementsystem.repository.model.User;
import com.gmail.vladbaransky.newsmanagementsystem.service.UserService;
import com.gmail.vladbaransky.newsmanagementsystem.service.model.AppUserPrincipal;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;

@Service
public class AppUserDetailsService implements UserDetailsService {

    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private final UserService userService;

    public AppUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException("User is not found");
        }
        logger.info("User '" + username + "' was found");
        return new AppUserPrincipal(user);
    }
}
*/
