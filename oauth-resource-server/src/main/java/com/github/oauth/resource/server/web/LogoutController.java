package com.github.oauth.resource.server.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LogoutController {


    @GetMapping(value = "/oauth/logout", produces = "application/json;charset=UTF-8")
    public String[] getMessages(OAuth2Authentication authentication) {
        log.info("Logout for user {}!", authentication.getName());
        return new String[]{"Successfully logout"};
    }
}