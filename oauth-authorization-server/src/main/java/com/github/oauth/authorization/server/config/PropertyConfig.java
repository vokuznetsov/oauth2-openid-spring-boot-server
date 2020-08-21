package com.github.oauth.authorization.server.config;

import com.github.oauth.authorization.server.property.OAuthServerProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertyConfig {

    @Bean
    @ConfigurationProperties(prefix = "app.oauth.server")
    public OAuthServerProperty serverProperty() {
        return new OAuthServerProperty();
    }
}
