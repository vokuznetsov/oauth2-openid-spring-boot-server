package com.github.oauth.resource.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.github.oauth.resource.server")
public class OAuthResourceServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthResourceServerApplication.class, args);
    }
}
