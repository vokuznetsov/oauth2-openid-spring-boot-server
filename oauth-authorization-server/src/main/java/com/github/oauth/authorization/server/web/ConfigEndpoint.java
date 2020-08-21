package com.github.oauth.authorization.server.web;

import com.github.oauth.authorization.server.property.OAuthServerProperty;
import com.github.oauth.authorization.server.property.OpenIdConfiguration;
import com.nimbusds.jose.jwk.JWKSet;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfigEndpoint {

    private final OAuthServerProperty serverProperty;
    private final JWKSet jwkAccessTokenSet;
    private final JWKSet jwkTokenIdSet;

    public ConfigEndpoint(OAuthServerProperty serverProperty,
            @Qualifier("access-token-jwk") JWKSet jwkAccessTokenSet,
            @Qualifier("token-id-jwk") JWKSet jwkTokenIdSet) {
        this.serverProperty = serverProperty;
        this.jwkAccessTokenSet = jwkAccessTokenSet;
        this.jwkTokenIdSet = jwkTokenIdSet;
    }


    @GetMapping("/.well-known/openid-configuration")
    @ResponseBody
    public OpenIdConfiguration getOpenIdConfiguration() {
        return serverProperty.getOpenIdConfiguration();
    }

    @GetMapping(value = "/oauth/access/token/jwk", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String accessTokenKeys() {
        return jwkAccessTokenSet.toString();
    }

    @GetMapping(value = "/oauth/openid/jwk", produces = "application/json; charset=UTF-8")
    @ResponseBody
    public String tokenIdKeys() {
        return jwkTokenIdSet.toString();
    }

}