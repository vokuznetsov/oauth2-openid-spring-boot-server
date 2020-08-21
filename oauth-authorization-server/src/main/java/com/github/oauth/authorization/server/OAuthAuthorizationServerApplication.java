package com.github.oauth.authorization.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.oauth.authorization.server")
public class OAuthAuthorizationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthAuthorizationServerApplication.class, args);
    }
}
