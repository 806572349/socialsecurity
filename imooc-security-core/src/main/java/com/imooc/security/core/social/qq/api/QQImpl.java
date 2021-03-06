package com.imooc.security.core.social.qq.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.social.oauth2.AbstractOAuth2ApiBinding;
import org.springframework.social.oauth2.TokenStrategy;
import org.springframework.stereotype.Component;

@Slf4j
public class QQImpl extends AbstractOAuth2ApiBinding implements QQ {
    //https://graph.qq.com/oauth2.0/me?access_token=YOUR_ACCESS_TOKEN
    //https://graph.qq.com/user/get_user_info
    private static final String OPenId = "https://graph.qq.com/oauth2.0/me?access_token=%s";
    private static final String UserInfo = "https://graph.qq.com/user/get_user_info?oauth_consumer_key=%s&openid=%s";
    private String appId;
    private String openId;

    private ObjectMapper objectMapper = new ObjectMapper();

    public QQImpl(String accessToken, String appId) {
        super(accessToken, TokenStrategy.OAUTH_TOKEN_PARAMETER);
        this.appId = appId;
        String url = String.format(OPenId, accessToken);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info("result:" + result);
        this.openId = StringUtils.substringBetween(result, "\"openid\":\"", "\"}");
    }

    @Override
    public QQUserInfo getUserInfo() {
        String url = String.format(UserInfo, appId, openId);
        String result = getRestTemplate().getForObject(url, String.class);
        log.info(result);
        QQUserInfo userInfo=null;
        try {
            userInfo=objectMapper.readValue(result, QQUserInfo.class);
            userInfo.setOpenId(openId);
            return userInfo;
        } catch (Exception e) {

            throw new RuntimeException("获取用户信息失败");
        }
    }
}
