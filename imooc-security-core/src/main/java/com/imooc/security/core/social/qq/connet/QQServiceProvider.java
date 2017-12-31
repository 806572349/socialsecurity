package com.imooc.security.core.social.qq.connet;


import com.imooc.security.core.social.qq.api.QQ;
import com.imooc.security.core.social.qq.api.QQImpl;
import org.springframework.social.oauth2.AbstractOAuth2ServiceProvider;

public class QQServiceProvider  extends AbstractOAuth2ServiceProvider<QQ> {
    private String appId;
    private static  final String authorizeUrl="https://graph.qq.com/oauth2.0/authorize";
    private static final String Access_Token="https://graph.qq.com/oauth2.0/token";
    //101446051
    /**
     *
     */
    public QQServiceProvider(String appId,String appSecret ) {
        super(new QQOAuth2Template(appId,appSecret,authorizeUrl,Access_Token));
        this.appId = appId;
    }

    @Override
    public QQ getApi(String accessToken) {


        return new QQImpl(accessToken,appId);
    }
}
