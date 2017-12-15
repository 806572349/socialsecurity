package com.imooc.security.app.authentication.server.config;

import com.imooc.security.app.authentication.jwt.JwtTokenEnhancer;
import com.imooc.security.core.properties.Oauth2ClientProperties;
import com.imooc.security.core.properties.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableAuthorizationServer
public class ServerConfig  extends AuthorizationServerConfigurerAdapter{
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private TokenStore tokenStore;
    @Autowired(required = false)
    private JwtAccessTokenConverter jwtAccessTokenConverter;

//    @Autowired(required = false)
//    private TokenEnhancer tokenEnhancer;
    //入口点
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore)
                .authenticationManager(authenticationManager).userDetailsService(userDetailsService);

        //这是增加jwt额外信息的
        if (jwtAccessTokenConverter!=null){

            JwtTokenEnhancer jwtTokenEnhancer=new JwtTokenEnhancer();
            TokenEnhancerChain tokenEnhancerChain=new TokenEnhancerChain();
            List<TokenEnhancer> list=new ArrayList<>();
            list.add(jwtTokenEnhancer);
            list.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(list);
            endpoints.tokenEnhancer(tokenEnhancerChain);
        }
        endpoints.accessTokenConverter(jwtAccessTokenConverter);
    }

    //客户端获取令牌 这个方法可以再配置文件配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
      /*  clients.inMemory().withClient("imooc")
                .secret("imooc")
                .accessTokenValiditySeconds(7200)
                .authorizedGrantTypes("refresh_token","passwrod")//授权模式
                .scopes("all","write")
        .and().withClient("xxxx")*/
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())){
            for (Oauth2ClientProperties config:
            securityProperties.getOauth2().getClients()) {
                builder.withClient(config.getClientId())
                        .secret(config.getClientSecret())
                        .accessTokenValiditySeconds(config.getAccessTokenValiditySeconds())
                        .authorizedGrantTypes("refresh_token","password")//授权模式
                        .scopes("all","write");
            }
        }

    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("isAuthenticated()");

    }

}
