package com.imooc.security.app.authentication.jwt;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class JwtTokenEnhancer implements TokenEnhancer {
    private Map<String,Object> map;

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        Map<String,Object> map=new HashMap<>();
        map.put("csn","csn");
        ((DefaultOAuth2AccessToken)(accessToken)).setAdditionalInformation(map);
        return accessToken;
    }
}
