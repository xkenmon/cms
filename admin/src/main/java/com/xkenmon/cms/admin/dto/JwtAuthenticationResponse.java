package com.xkenmon.cms.admin.dto;


/**
 * @author bigmeng
 * @date 2018/8/9
 */
public class JwtAuthenticationResponse {
    private String tokenType = "Bearer";
    private String accessToken;
    private Long expirationInMs;

    public JwtAuthenticationResponse(String accessToken,Long expirationInMs) {
        this.accessToken = accessToken;
        this.expirationInMs = expirationInMs;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpirationInMs() {
        return expirationInMs;
    }

    public void setExpirationInMs(Long expirationInMs) {
        this.expirationInMs = expirationInMs;
    }
}
