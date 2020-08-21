package com.github.oauth.authorization.server.service;

import com.github.oauth.authorization.server.dto.CommonUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommonUserDetailsService implements UserDetailsService {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String EMAIL = "admin@admin.com";

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new CommonUserDetails(USERNAME, "{noop}" + PASSWORD, EMAIL);
    }
}
