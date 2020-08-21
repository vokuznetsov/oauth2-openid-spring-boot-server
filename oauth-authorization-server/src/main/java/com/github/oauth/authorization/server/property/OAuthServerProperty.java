package com.github.oauth.authorization.server.property;

import lombok.Data;

@Data
public class OAuthServerProperty {

    private ServerMetadata metadata;
    private OpenIdConfiguration openIdConfiguration;
    private JwtProperty jwtProperty;

    @Data
    public static class ServerMetadata {

        private String host;
        private String port;
        private String url;
    }

    @Data
    public static class JwtProperty {

        private Long expirationTime;
    }
}
