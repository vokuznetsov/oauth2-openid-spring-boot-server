package com.github.oauth.authorization.server.property;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenIdConfiguration {

    /* REQUIRED. URL using the https scheme with no query or fragment component that the OP asserts
    as its Issuer Identifier.If Issuer discovery is supported (see Section 2),
    this value MUST be identical to the  issuer value returned by WebFinger.
    This also MUST be identical to the iss Claim value in ID Tokens issued from this Issuer. */
    private String issuer;

    /* REQUIRED. URL of the OP's OAuth 2.0 Authorization Endpoint [OpenID.Core]. */
    @JsonProperty("authorization_endpoint")
    private String authorizationEndpoint;

    /* URL of the OP's OAuth 2.0 Token Endpoint [OpenID.Core].
    This is REQUIRED unless only the Implicit Flow is used. */
    @JsonProperty("token_endpoint")
    private String tokenEndpoint;

    /* RECOMMENDED. URL of the OP's UserInfo Endpoint [OpenID.Core].
    This URL MUST use the https scheme and MAY contain port, path, and query parameter components. */
    @JsonProperty("userinfo_endpoint")
    private String userinfoEndpoint;

    /* REQUIRED. URL of the OP's JSON Web Key Set [JWK] document.
      This contains the signing key(s) the RP uses to validate signatures from the OP.
      The JWK Set MAY also contain the Server's encryption key(s),
      which are used by RPs to encrypt requests to the Server.
      When both signing and encryption keys are made available,a use (Key Use) parameter value is REQUIRED
      for all keys in the referenced JWK Set to indicate each key's intended usage.
      Although some algorithms allow the same key to be used for both signatures and encryption,
      doing so is NOT RECOMMENDED, as it is less secure. The JWK x5c parameter MAY be used
      to provide X.509 representations of keys provided. When used, the bare key values MUST still be present
      and MUST match those in the certificate. */
    @JsonProperty("jwks_uri")
    private String jwksUri;

    /* RECOMMENDED. URL of the OP's Dynamic Client Registration Endpoint [OpenID.Registration]. */
    @JsonProperty("registration_endpoint")
    private String registrationEndpoint;

    /* RECOMMENDED. JSON array containing a list of the OAuth 2.0 [RFC6749] scope values that this server supports.
    The server MUST support the openid scope value. Servers MAY choose not to advertise some supported scope values
    even when this parameter is used, although those defined in [OpenID.Core] SHOULD be listed, if supported. */
    @JsonProperty("scopes_supported")
    private List<String> scopesSupported;

    /* REQUIRED. JSON array containing a list of the OAuth 2.0 response_type values that this OP supports.
     Dynamic OpenID Providers MUST support the code, id_token, and the token id_token Response Type values. */
    @JsonProperty("response_types_supported")
    private List<String> responseTypesSupported;

    /* OPTIONAL. JSON array containing a list of the OAuth 2.0 response_mode values that this OP supports
     as specified in OAuth 2.0 Multiple Response Type Encoding Practices [OAuth.Responses].
     If omitted, the default for Dynamic OpenID Providers is ["query", "fragment"]. */
    @JsonProperty("response_modes_supported")
    private List<String> responseModesSupported;

    /* OPTIONAL. JSON array containing a list of the OAuth 2.0 Grant Type values that this OP supports.
     Dynamic OpenID Providers MUST support the authorization_code and implicit Grant Type values
     and MAY support other Grant Types. If omitted, the default value is ["authorization_code", "implicit"]. */
    @JsonProperty("grant_types_supported")
    private List<String> grantTypesSupported;

    /* OPTIONAL. JSON array containing a list of the Authentication Context Class References that this OP supports. */
    @JsonProperty("acr_values_supported")
    private List<String> acrValuesSupported;

    /* REQUIRED. JSON array containing a list of the Subject Identifier types that this OP supports.
    Valid types include pairwise and public. */
    @JsonProperty("subject_types_supported")
    private List<String> subjectTypesSupported;

    /* REQUIRED. JSON array containing a list of the JWS signing algorithms (alg values)
     supported by the OP for the ID Token to encode the Claims in a JWT [JWT].
     The algorithm RS256 MUST be included. The value none MAY be supported,
     but MUST NOT be used unless the Response Type used returns no ID Token from the Authorization Endpoint
     (such as when using the Authorization Code Flow). */
    @JsonProperty("id_token_signing_alg_values_supported")
    private List<String> idTokenSigningAlgValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWE encryption algorithms (alg values)
     supported by the OP for the ID Token to encode the Claims in a JWT [JWT]. */
    @JsonProperty("id_token_encryption_alg_values_supported")
    private List<String> idTtokenEncryptionAlgValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWE encryption algorithms (enc values)
     supported by the OP for the ID Token to encode the Claims in a JWT [JWT]. */
    @JsonProperty("id_token_encryption_enc_values_supported")
    private List<String> idTokenEncryptionEncValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWS [JWS] signing algorithms (alg values) [JWA]
    supported by the UserInfo Endpoint to encode the Claims in a JWT [JWT]. The value none MAY be included. */
    @JsonProperty("userinfo_signing_alg_values_supported")
    private List<String> userinfoSigningAlgValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWE [JWE] encryption algorithms (alg values) [JWA]
     supported by the UserInfo Endpoint to encode the Claims in a JWT [JWT]. */
    @JsonProperty("userinfo_encryption_alg_values_supported")
    private List<String> userinfoEncryptionAlgValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWE encryption algorithms (enc values) [JWA]
    supported by the UserInfo Endpoint to encode the Claims in a JWT [JWT]. */
    @JsonProperty("userinfo_encryption_enc_values_supported")
    private List<String> userinfoEncryptionEncValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWS signing algorithms (alg values) supported
     by the OP for Request Objects, which are described in Section 6.1 of OpenID Connect Core 1.0 [OpenID.Core].
     These algorithms are used both when the Request Object is passed by value (using the request parameter)
     and when it is passed by reference (using the request_uri parameter). Servers SHOULD support none and RS256. */
    @JsonProperty("request_object_signing_alg_values_supported")
    private List<String> requestObjectSigningAlgValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWE encryption algorithms (alg values) supported
    by the OP for Request Objects. These algorithms are used both when the Request Object is passed by value
    and when it is passed by reference. */
    @JsonProperty("request_object_encryption_alg_values_supported")
    private List<String> requestObjectEncryptionAlgValuesSupported;

    /* OPTIONAL. JSON array containing a list of the JWE encryption algorithms (enc values) supported
     by the OP for Request Objects. These algorithms are used both when the Request Object is passed
     by value and when it is passed by reference. */
    @JsonProperty("request_object_encryption_enc_values_supported")
    private List<String> requestObjectEncryptionEncValuesSupported;

    /* OPTIONAL. JSON array containing a list of Client Authentication methods supported by this Token Endpoint.
     The options are client_secret_post, client_secret_basic, client_secret_jwt, and private_key_jwt,
     as described in Section 9 of OpenID Connect Core 1.0 [OpenID.Core].
     Other authentication methods MAY be defined by extensions.
     If omitted, the default is client_secret_basic -- the HTTP Basic Authentication Scheme
     specified in Section 2.3.1 of OAuth 2.0 [RFC6749]. */
    @JsonProperty("token_endpoint_auth_methods_supported")
    private List<String> tokenEndpointAuthMethodsSupported;

    /* OPTIONAL. JSON array containing a list of the JWS signing algorithms (alg values)
    supported by the Token Endpoint for the signature on the JWT [JWT] used
    to authenticate the Client at the Token Endpoint
    for the private_key_jwt and client_secret_jwt authentication methods. Servers SHOULD support RS256.
    The value none MUST NOT be used. */
    @JsonProperty("token_endpoint_auth_signing_alg_values_supported")
    private List<String> tokenEndpointAuthSigningAlgValuesSupported;

    /* OPTIONAL. JSON array containing a list of the display parameter values that the OpenID Provider supports.
    These values are described in Section 3.1.2.1 of OpenID Connect Core 1.0 [OpenID.Core]. */
    @JsonProperty("display_values_supported")
    private List<String> displayValuesSupported;

    /* OPTIONAL. JSON array containing a list of the Claim Types that the OpenID Provider supports.
    These Claim Types are described in Section 5.6 of OpenID Connect Core 1.0 [OpenID.Core].
    Values defined by this specification are normal, aggregated, and distributed.
    If omitted, the implementation supports only normal Claims. */
    @JsonProperty("claim_types_supported")
    private List<String> claimTypesSupported;

    /* RECOMMENDED. JSON array containing a list of the Claim Names of the Claims
     that the OpenID Provider MAY be able to supply values for.
     Note that for privacy or other reasons, this might not be an exhaustive list. */
    @JsonProperty("claims_supported")
    private List<String> claimsSupported;

    /* OPTIONAL. URL of a page containing human-readable information that developers might want
    or need to know when using the OpenID Provider.
    In particular, if the OpenID Provider does not support Dynamic Client Registration,
    then information on how to register Clients needs to be provided in this documentation. */
    @JsonProperty("service_documentation")
    private String serviceDocumentation;

    /* OPTIONAL. Languages and scripts supported for values in Claims being returned,
     represented as a JSON array of BCP47 [RFC5646] language tag values.
     Not all languages and scripts are necessarily supported for all Claim values. */
    @JsonProperty("claims_locales_supported")
    private List<String> claimsLocalesSupported;

    /* OPTIONAL. Languages and scripts supported for the user interface,
     represented as a JSON array of BCP47 [RFC5646] language tag values. */
    @JsonProperty("ui_locales_supported")
    private List<String> uiLocalesSupported;

    /* OPTIONAL. Boolean value specifying whether the OP supports use of the claims parameter,
     with true indicating support. If omitted, the default value is false. */
    @JsonProperty("claims_parameter_supported")
    private Boolean claimsParameterSupported;

    /* OPTIONAL. Boolean value specifying whether the OP supports use of the request parameter,
    with true indicating support. If omitted, the default value is false. */
    @JsonProperty("request_parameter_supported")
    private Boolean requestParameterSupported;

    /* OPTIONAL. Boolean value specifying whether the OP supports use of the request_uri parameter,
     with true indicating support. If omitted, the default value is true. */
    @JsonProperty("request_uri_parameter_supported")
    private Boolean requestUriParameterSupported;

    /* OPTIONAL. Boolean value specifying whether the OP requires any request_uri values used
     to be pre-registered using the request_uris registration parameter.
     Pre-registration is REQUIRED when the value is true. If omitted, the default value is false. */
    @JsonProperty("require_request_uri_registration")
    private Boolean requireRequestUriRegistration;

    /* OPTIONAL. URL that the OpenID Provider provides to the person registering the Client
     to read about the OP's requirements on how the Relying Party can use the data provided by the OP.
     The registration process SHOULD display this URL to the person registering the Client if it is given. */
    @JsonProperty("op_policy_uri")
    private String opPolicyUri;

    /* OPTIONAL. URL that the OpenID Provider provides to the person registering the Client
     to read about OpenID Provider's terms of service.
     The registration process SHOULD display this URL to the person registering the Client if it is given. */
    @JsonProperty("op_tos_uri")
    private String opTosUri;

}