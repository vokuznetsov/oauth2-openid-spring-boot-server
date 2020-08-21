package com.github.oauth.authorization.server.config.keys;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfig {

    @Bean
    @Qualifier("access-token-jwk")
    public JWKSet jwkAccessTokenSet() {
        RSAKey.Builder builder = new RSAKey.Builder(AccessTokenKeyConfig.getVerifierKey())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(AccessTokenKeyConfig.VERIFIER_KEY_ID);
        return new JWKSet(builder.build());
    }

    @Bean
    @Qualifier("token-id-jwk")
    public JWKSet jwkOpenConnectTokenIdSet() {
        RSAKey.Builder builder = new RSAKey.Builder(OpenIdTokenKeyConfig.getVerifierKey())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID(OpenIdTokenKeyConfig.VERIFIER_KEY_ID);
        return new JWKSet(builder.build());
    }
}
