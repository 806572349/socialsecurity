package com.imooc.security.core.social.qq.connet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.social.oauth2.AccessGrant;
import org.springframework.social.oauth2.OAuth2Template;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;

@Slf4j
public class QQOAuth2Template extends OAuth2Template {
    public QQOAuth2Template(String clientId, String clientSecret, String authorizeUrl, String accessTokenUrl) {
        super(clientId, clientSecret, authorizeUrl, accessTokenUrl);
        setUseParametersForClientAuthentication(true); //必须设置为true 请求时就会带上clientid
    }


    /**
     *
     * 根据返回过来的数据，进行处理，这里是qq响应是一段字符串
     * @param accessTokenUrl
     * @param parameters
     * @return
     */
    @Override
    protected AccessGrant postForAccessGrant(String accessTokenUrl, MultiValueMap<String, String> parameters) {
        String responseStr=getRestTemplate().postForObject(accessTokenUrl,parameters,String.class);
        log.info("获取accessToken的响应"+responseStr);
        String[] items= StringUtils.splitByWholeSeparatorPreserveAllTokens(responseStr,"&");
        String accessToken = StringUtils.substringAfterLast(items[0], "=");
        Long expiresIn=new Long(StringUtils.substringAfterLast(items[1],"="));
        String refreshToken=StringUtils.substringAfterLast(items[2],"=");


        return new AccessGrant(accessToken,null,refreshToken,expiresIn);
    }

    /**
     * 加上新的 StringHttpMessageConverter
     * @return
     */
    @Override
    protected RestTemplate createRestTemplate() {
        RestTemplate restTemplate = super.createRestTemplate();
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter(Charset.forName("UTF-8")));//text/html
        return  restTemplate;
    }
}
