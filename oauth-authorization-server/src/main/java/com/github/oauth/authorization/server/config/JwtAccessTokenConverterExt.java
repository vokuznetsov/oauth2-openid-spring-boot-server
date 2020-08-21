package com.github.oauth.authorization.server.config;

import com.github.oauth.authorization.server.config.keys.AccessTokenKeyConfig;
import com.github.oauth.authorization.server.config.keys.OpenIdTokenKeyConfig;
import com.github.oauth.authorization.server.dto.CommonUserDetails;
import com.github.oauth.authorization.server.dto.EmailDto;
import com.github.oauth.authorization.server.property.OAuthServerProperty;
import com.github.oauth.authorization.server.property.OpenIdConfiguration;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.util.JsonParserFactory;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
public class JwtAccessTokenConverterExt extends JwtAccessTokenConverter {

    private static final String KEY_ID_HEADER = "kid";

    private static final String TOKEN_ID_INFO = "token_id";
    private static final String EMAIL_INFO = "email";

    private static final String SCOPE_REQUEST_PARAM = "scope";

    private final OAuthServerProperty serverProperty;
    private final ApprovalStore approvalStore;


    @Override
    protected String encode(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        String content;
        try {
            addAdditionalInformation(accessToken, authentication);

            Map<String, ?> map = getAccessTokenConverter().convertAccessToken(accessToken, authentication);
            content = JsonParserFactory.create().formatMap(map);
        } catch (Exception ex) {
            throw new IllegalStateException("Cannot convert access token to JSON", ex);
        }

        Map<String, String> headers = new HashMap<>();
        headers.put(KEY_ID_HEADER, AccessTokenKeyConfig.VERIFIER_KEY_ID);
        return JwtHelper.encode(content, AccessTokenKeyConfig.getSigner(), headers).getEncoded();
    }

    private void addAdditionalInformation(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String, Object> additionalInformation = getAdditionalInfoByScope(authentication);

        DefaultOAuth2AccessToken tempResult = (DefaultOAuth2AccessToken) accessToken;
        tempResult.getAdditionalInformation().putAll(additionalInformation);
    }

    private Map<String, Object> getAdditionalInfoByScope(OAuth2Authentication authentication) {
        Map<String, Object> additionalInformation = new HashMap<>();

        CommonUserDetails userDetails = (CommonUserDetails) authentication.getPrincipal();
        OAuth2Request oauth2Request = authentication.getOAuth2Request();

        Set<String> requestedScope = getRequestedScopes(userDetails, oauth2Request);

        if (requestedScope.contains("openid")) {
            String infoValue = createJwtTokenId(userDetails.getUsername(), oauth2Request.getClientId());
            additionalInformation.put(TOKEN_ID_INFO, infoValue);
        }

        if (requestedScope.contains("email")) {
            EmailDto email = new EmailDto(userDetails.getEmail());
            additionalInformation.put(EMAIL_INFO, email);
        }

        return additionalInformation;
    }

    private String createJwtTokenId(String subject, String clientId) {
        OpenIdConfiguration openIdConfiguration = serverProperty.getOpenIdConfiguration();

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.RS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder()
                .setId(OpenIdTokenKeyConfig.VERIFIER_KEY_ID)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(openIdConfiguration.getIssuer())
                .setAudience(clientId)
                .signWith(signatureAlgorithm, OpenIdTokenKeyConfig.getSignerKey());

        long expMillis = nowMillis + serverProperty.getJwtProperty().getExpirationTime();
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }

    private Set<String> getRequestedScopes(CommonUserDetails user, OAuth2Request oauth2Request) {
        String scopesStr = oauth2Request.getRequestParameters().get(SCOPE_REQUEST_PARAM);
        if (StringUtils.isEmpty(scopesStr)) {
            return new HashSet<>();
        }

        Set<String> requestedScope = Stream.of(scopesStr.split("\\s+")).collect(Collectors.toSet());
        Collection<Approval> approvals = approvalStore.getApprovals(user.getUsername(), oauth2Request.getClientId());

        return approvals.stream()
                .filter(approval -> approval.isApproved() && requestedScope.contains(approval.getScope()))
                .map(Approval::getScope)
                .collect(Collectors.toSet());
    }
}
