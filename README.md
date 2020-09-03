# OAuth2-Openid-Connect Spring-boot Server
Implementation OAuth2 + OpenId Connect Server using Spring-boot

## 1. How to work with OAuth2 server?

### 1.1 Get short-lived code.
    
   1.1.1. Request to the **_/oauth/authorize_** (GET) endpoint
        
    http://localhost:8183/oauth/authorize?response_type=code&client_id=client&scope=openid%20email
    
   1.1.2. Enter _username/password_ (admin/admin) to returned form.

### 1.2 Get access_toke, refresh_token, token_id and other meta information.

 Request to **_/oauth/token_** (POST). 
    
   It is necessary to add headers (actual for grant_type(s) _authorization_code_, _refresh_token_ and _password_):
   
    * Authorization: Basic Y2xpZW50OmNsaWVudA==         
    * Content-Type: application/x-www-form-urlencoded 
   
   Authorization header is Base64 encode of (_client_id:client_secret_).
   
#### 1.2.1 authorization_code grant type.
    
   Field `code` is returned value from step `1.1.2`.     
   
    http://localhost:8183/oauth/token?grant_type=authorization_code&scope=openid email&code=xptJCt&redirect_uri=http%3A%2F%2Flocalhost%3A8183
     
#### 1.2.2 password grant_type.
    
    http://localhost:8183/oauth/token?grant_type=password&scope=openid%20email&username=admin&password=admin    
        
#### 1.2.3 refresh_token grant_type.

   Refresh token from `1.2.1` step.
    
    http://localhost:8183/oauth/token?grant_type=refresh_token&refresh_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik4xN2taKzRlK2YxeWh1ZXlsTVFDRzhnNm9KdlJocy9DUHhJcm9PZzkzM2s9In0.eyJ0b2tlbl9pZCI6ImV5SmhiR2NpT2lKU1V6STFOaUo5LmV5SnFkR2tpT2lKMFdIaEJOVWt3VlhSSmFtZHZNbkJ2ZENzNVpVdGhSRWxYVW5sVlRsTnllVFZ2VkV4ck9GZFVSMDVKUFNJc0ltbGhkQ0k2TVRVNU9EQXdNakExTVN3aWMzVmlJam9pWVdSdGFXNGlMQ0pwYzNNaU9pSm9kSFJ3T2k4dmJHOWpZV3hvYjNOME9qZ3hPRE1pTENKaGRXUWlPaUpqYkdsbGJuUWlMQ0psZUhBaU9qRTFPVGd3TURVMk5URjkuaExxczhDOWZ1eDBDUEhibGE2NzhoSlVGdnFzMUJsY245ZDdNWEtSQ1BxYlMzWXBUdHJJYkVxeDZGNFI1bXhyS0VQamY2RTl1U2RJY2dRa2xtalJzUVhhdHgtMU4zOVJqNVU5RHI5ZWNOaFBBVGd6blRLTWZyTDRjSnB3eXVGYjlDLVlDdTVqa0hmRW0xSWw0TV9CcHNUeWVHVVNJNEEySDd2SDU0MXZXYkRoS1ZXX1dXSTFSOF9mUkpqRGhJUGk3YU9qTGFOWlNDM25ZWjdXLUxvUmNwUHpQQmI2R0Rlam1HR1FYcG1WNmZ4SVdTY0FISl9QSGJ1dGZWUGRQV1FpRzVuMURjSlZVRzVpMkt5TDJyeG5Ca05ic3dlOGdLN2RrM3V4LUpGMVhORlZsM2p0b2NrQlZaWVR3RXFoRU02N2ZoTHZJdEY4X0k5di10SG9Vd3N3Wkt3IiwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJvcGVuaWQiLCJlbWFpbCJdLCJhdGkiOiJFS1NrbXc2VTVHSVVIS1dmQ0xscGV3TUpuR0UiLCJleHAiOjE2MDA1OTQwNTEsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiLWxEVmVKR3NILVZCVDN3eDVKVDlKLXh4aTlrIiwiZW1haWwiOnsiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20ifSwiY2xpZW50X2lkIjoiY2xpZW50In0.Wcuc_vYjra05MsvAER5_dKUaqhiLJzPtvObV2TsgbJBdVlO4CLosWAWf38p5IAaEaoSflL2aYo3NrwGkKxlHurtTP6oS7qTelIzxJfG0ta7e_vQgHElRQiZPYOmefm59_l0KxiJTFYm430vJOy_L8Od7LUJr1JDLEmHev7q2oqD3NfADKsJlZbzHUrGIwikwtntTd9RSGODwQDGY-DiFB837zJQwxpy_I8XpAV9GTWQlqGGZy3XZPD_DJqCPJo98PH0t9EF1fEtr6_uYIWp2WvlTBxB5UnJOJK04JmKXGMz3G1X3TLZ4VuE2a4MQPCjnCkclDpShsKRFUb-m1R4D2Q
         
### 1.3 Check access_token.

1.3.1. Add `Authorization` header from `1.2` step.

1.3.2. Request to **_/oauth/check_token_** (POST). Token is access_token from `1.2.?` step.

    http://localhost:8183/oauth/check_token?token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCIsImtpZCI6Ik4xN2taKzRlK2YxeWh1ZXlsTVFDRzhnNm9KdlJocy9DUHhJcm9PZzkzM2s9In0.eyJ0b2tlbl9pZCI6ImV5SmhiR2NpT2lKU1V6STFOaUo5LmV5SnFkR2tpT2lKMFdIaEJOVWt3VlhSSmFtZHZNbkJ2ZENzNVpVdGhSRWxYVW5sVlRsTnllVFZ2VkV4ck9GZFVSMDVKUFNJc0ltbGhkQ0k2TVRVNU9EQXdNakExTVN3aWMzVmlJam9pWVdSdGFXNGlMQ0pwYzNNaU9pSm9kSFJ3T2k4dmJHOWpZV3hvYjNOME9qZ3hPRE1pTENKaGRXUWlPaUpqYkdsbGJuUWlMQ0psZUhBaU9qRTFPVGd3TURVMk5URjkuaExxczhDOWZ1eDBDUEhibGE2NzhoSlVGdnFzMUJsY245ZDdNWEtSQ1BxYlMzWXBUdHJJYkVxeDZGNFI1bXhyS0VQamY2RTl1U2RJY2dRa2xtalJzUVhhdHgtMU4zOVJqNVU5RHI5ZWNOaFBBVGd6blRLTWZyTDRjSnB3eXVGYjlDLVlDdTVqa0hmRW0xSWw0TV9CcHNUeWVHVVNJNEEySDd2SDU0MXZXYkRoS1ZXX1dXSTFSOF9mUkpqRGhJUGk3YU9qTGFOWlNDM25ZWjdXLUxvUmNwUHpQQmI2R0Rlam1HR1FYcG1WNmZ4SVdTY0FISl9QSGJ1dGZWUGRQV1FpRzVuMURjSlZVRzVpMkt5TDJyeG5Ca05ic3dlOGdLN2RrM3V4LUpGMVhORlZsM2p0b2NrQlZaWVR3RXFoRU02N2ZoTHZJdEY4X0k5di10SG9Vd3N3Wkt3IiwidXNlcl9uYW1lIjoiYWRtaW4iLCJzY29wZSI6WyJvcGVuaWQiLCJlbWFpbCJdLCJleHAiOjE1OTgwNDUyNTEsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiRUtTa213NlU1R0lVSEtXZkNMbHBld01KbkdFIiwiZW1haWwiOnsiZW1haWwiOiJhZG1pbkBhZG1pbi5jb20ifSwiY2xpZW50X2lkIjoiY2xpZW50In0.TKbSwc6Oy9l8ptEXOh5yhaDlV72J3RZ8eTS5xeK4dEOkhqTkqI2bVE39sf5EQw1T9FpPI32Pz8O0FS56zBNzfQAnExD6EkU6WQuqFk0PEH5Gb9EjMHp2QuxTao9gWKrJar3BX086vCWzz-i2-yuczxT_qeLCIwdzw1cQqp28m9ROCuhGlsGybwoYAtQLwU6qSpk5VG85Obq5nCOTn5S91gWyrx8FjUiGBVsw3TA6AGGoVO8R57jaebxbTp8ddOgqrI_FK-i1Mh6t81xBvgsnEyJRjMCQ_FpP_i2B648FSfzWNDukHsd2xzS8zdUysBSI9FXj45zUJAQWFUJ7CjcR_w

### 1.4 How to get JWK keys for access_token and token_id?
    
   Request JWK keys:
   * _http://localhost:8183/oauth/access/token/jwk_ (GET) - for access_token
   * _http://localhost:8183/oauth/openid/jwk_ (GET) - for token_id
   
### 1.5 How to get openid-configuration?
    
   * http://localhost:8183/.well-known/openid-configuration (GET)

## 2. How to verify access_token (JWT, OAuth2) and token_id (OpenId Connect) ?

2.1. Generate keystore for _access_token/refresh_token_ and _token_id_ (OpenId-Connect):

        keytool -genkey -noprompt \
          -alias oauth2-access-token \
          -dname "CN=, OU=, O=, L=, S=, C=" \
          -keyalg RSA -validity 36500 \
          -keystore access_token.keystore \
          -keypass HjEgf8RGpr6H9EmB \
          -storepass HjEgf8RGpr6H9EmB \
          -storetype JKS


2.2. How to verify signature of _access_token/token_id_
    
   2.2.1. Go to  https://jwt.io/ and paste _access_token/token_id_
    
   2.2.2. Request JWK keys:
      * http://localhost:8183/oauth/access/token/jwk - for access_token
      * http://localhost:8183/oauth/openid/jwk - for token_id
        
   2.2.3. Go to https://8gwifi.org/jwkconvertfunctions.jsp and copy result from step `2.2.2`. This site generates public key based on JWK key.
        You need to copy this part:
        
        {
            "kty": "RSA",
            "e": "AQAB",
            "use": "sig",
            "kid": "mJIR29xLoz8OItXHbX37kSF4tsTyq/1QoLmZgkudfqg=",
            "alg": "RS256",
            "n": "xcHs6WXaSzf_gG0qG_7wk8yVRrBBmjQwqGWrqNdM45Svi9qzCCpscZ9zn1bQbE-SGCJNw7KRKvq-vLNF3HBPpp4_W8Y4S9QXNufZ8muheRGjH90MqdM_Nu8AIQZtWHe-9FGlPJPBRufvnb5Oi4g8TXCBB2TDHkiFkE_N7ODy6M0EJeGwMaEajJIFzAf__tPp3zNuWCiWmhBhjbkviegId8dIJc34phvpccFE3egxuk9TEZDewXv5LL8aLRmEZRnqD8zmBJRbFE0RShsTGoF7C7Fd5ZeGwWbEvgWyvfACwwHb0AZQ8PuMU6ussjmXc-3hM4tA9wYs9B_xZGZsGhgPxQ"
        }
        
   2.2.4. Go to site from `2.1` step and paste the public key (generated from `2.2.3` step) to the field "Verify Signature".
   
   2.2.5. Done. 