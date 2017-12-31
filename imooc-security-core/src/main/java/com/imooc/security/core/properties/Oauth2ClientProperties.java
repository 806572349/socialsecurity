package com.imooc.security.core.properties;

public class Oauth2ClientProperties {
    private String clientId;
    private String clientSecret;
    private int accessTokenValiditySeconds=100; //要设置默认过期时间，不然不会过期

    private int refeshTokenValiditySeconds=200; //默认刷新TOKEN时间

    public int getRefeshTokenValiditySeconds() {
        return refeshTokenValiditySeconds;
    }

    public void setRefeshTokenValiditySeconds(int refeshTokenValiditySeconds) {
        this.refeshTokenValiditySeconds = refeshTokenValiditySeconds;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(int accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }
}
