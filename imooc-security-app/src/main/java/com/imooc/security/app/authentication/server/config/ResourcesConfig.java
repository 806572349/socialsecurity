package com.imooc.security.app.authentication.server.config;

import com.imooc.security.core.authentication.mobile.config.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.properties.valitate.filter.SmmValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableResourceServer
public class ResourcesConfig  extends ResourceServerConfigurerAdapter{
    @Autowired
    private SecurityProperties securityProperties;
    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Override
    public void configure(HttpSecurity http) throws Exception {

        SmmValidateCodeFilter smmValidateCodeFilter=new SmmValidateCodeFilter();
        smmValidateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        smmValidateCodeFilter.setSecurityProperties(securityProperties);
        smmValidateCodeFilter.afterPropertiesSet();

        http     .addFilterBefore(smmValidateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authenticaiton/require")
                .loginProcessingUrl("/authenticaiton/form")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()

                .authorizeRequests()
                .antMatchers("/authenticaiton/require",securityProperties.getBrower().getLoginPage(),"/code/*","/authentication/mobile").permitAll()
                .anyRequest()
                .authenticated()
                .and().csrf().disable()
                .apply(smsCodeAuthenticationSecurityConfig);


    }
}
