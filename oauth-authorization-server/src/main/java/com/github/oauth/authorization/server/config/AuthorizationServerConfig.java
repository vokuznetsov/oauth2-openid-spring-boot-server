package com.github.oauth.authorization.server.config;

import com.github.oauth.authorization.server.config.keys.AccessTokenKeyConfig;
import com.github.oauth.authorization.server.property.OAuthServerProperty;
import com.github.oauth.authorization.server.service.CommonClientDetailsService;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.approval.UserApprovalHandler;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@RequiredArgsConstructor
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    private final OAuthServerProperty serverProperty;
    private final UserDetailsService userDetailsService;
    private final CommonClientDetailsService clientDetailsService;
    private final AuthenticationManager authenticationManager;
    private final DataSource dataSource;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(clientDetailsService);
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints
                // authenticationManager needs only for grant_type=password
                .authenticationManager(this.authenticationManager)
                .tokenStore(tokenStore())
                // need for support refresh_token grant_type
                .userDetailsService(userDetailsService)
                .userApprovalHandler(userApprovalHandler())
                .accessTokenConverter(accessTokenConverter());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        security.checkTokenAccess("isAuthenticated()")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
    }

    @Bean
    public UserApprovalHandler userApprovalHandler() {
        ApprovalStoreUserApprovalHandler userApprovalHandler = new ApprovalStoreUserApprovalHandler();
        userApprovalHandler.setApprovalStore(approvalStore());
        userApprovalHandler.setClientDetailsService(clientDetailsService);
        userApprovalHandler.setRequestFactory(new DefaultOAuth2RequestFactory(clientDetailsService));
        return userApprovalHandler;
    }

    @Bean
    public TokenStore tokenStore() {
        JwtTokenStore tokenStore = new JwtTokenStore(accessTokenConverter());
        tokenStore.setApprovalStore(approvalStore());
        return tokenStore;
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverterExt(serverProperty, approvalStore());
        converter.setSigner(AccessTokenKeyConfig.getSigner());
        converter.setVerifier(new RsaVerifier(AccessTokenKeyConfig.getVerifierKey()));
        return converter;
    }

    @Bean
    public ApprovalStore approvalStore() {
        return new JdbcApprovalStore(dataSource);
    }
}
