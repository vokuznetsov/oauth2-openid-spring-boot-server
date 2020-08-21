package com.github.oauth.authorization.server.service;

import com.github.oauth.authorization.server.property.OAuthServerProperty;
import com.github.oauth.authorization.server.property.OpenIdConfiguration;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommonClientDetailsService implements ClientDetailsService {

    private static final String CLIENT_ID = "client";
    private static final String CLIENT_SECRET = "client";
    private static final Set<String> REDIRECT_URIS = Set.of("http://localhost:8183");

    private final OAuthServerProperty serverProperty;

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        OpenIdConfiguration openIdConfiguration = serverProperty.getOpenIdConfiguration();

        BaseClientDetails clientDetails = new BaseClientDetails();
        clientDetails.setClientId(CLIENT_ID);
        clientDetails.setClientSecret("{noop}" + CLIENT_SECRET);
        clientDetails.setAuthorizedGrantTypes(openIdConfiguration.getGrantTypesSupported());
        clientDetails.setScope(openIdConfiguration.getScopesSupported());
        clientDetails.setRegisteredRedirectUri(REDIRECT_URIS);

        return clientDetails;
    }
}
