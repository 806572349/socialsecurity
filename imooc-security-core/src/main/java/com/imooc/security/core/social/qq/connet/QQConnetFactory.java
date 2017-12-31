package com.imooc.security.core.social.qq.connet;

import com.imooc.security.core.social.qq.api.QQ;
import org.springframework.social.connect.support.OAuth2ConnectionFactory;

public class QQConnetFactory extends OAuth2ConnectionFactory<QQ> {
    /**
     * Create a {@link OAuth2ConnectionFactory}.
     *

     */
    public QQConnetFactory(String providerId, String appId,String appsecret) {
        super(providerId, new QQServiceProvider(appId,appsecret), new QQAdapter());
    }
}
