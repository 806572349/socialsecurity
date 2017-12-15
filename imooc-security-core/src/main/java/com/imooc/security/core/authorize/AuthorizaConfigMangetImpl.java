package com.imooc.security.core.authorize;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class AuthorizaConfigMangetImpl implements AuthorizeConfigManger {
    @Autowired
    private Set<AuthorizaConfigProvider> authorizaConfigProviders;
    @Override
    public void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        for (AuthorizaConfigProvider authorizaConfigProvider:
                authorizaConfigProviders) {
            authorizaConfigProvider.config(config);
        }
        config.anyRequest().authenticated();
    }
}
