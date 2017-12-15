package com.imooc.security.brower;

import com.imooc.security.core.authentication.mobile.SmsCodeAuthenticationFilter;
import com.imooc.security.core.authentication.mobile.config.SmsCodeAuthenticationSecurityConfig;
import com.imooc.security.core.authorize.AuthorizeConfigManger;
import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.properties.valitate.filter.SmmValidateCodeFilter;
import com.imooc.security.core.properties.valitate.filter.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class BrowserSecurityConfig  extends WebSecurityConfigurerAdapter{


    @Autowired
    private SecurityProperties securityProperties;



    @Autowired
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    @Autowired
    private AuthenticationFailureHandler authenticationFailureHandler;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // region   记住我 功能的实现
    @Autowired
    private DataSource dataSource;
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
//        tokenRepository.setCreateTableOnStartup(true);自动配置创建表
        return  tokenRepository;

    }
    //endregion

    /*@Autowired
    AuthorizeConfigManger authorizeConfigManger;*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ValidateCodeFilter validateCodeFilter=new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        validateCodeFilter.setSecurityProperties(securityProperties);
        validateCodeFilter.afterPropertiesSet();

        SmmValidateCodeFilter smmValidateCodeFilter=new SmmValidateCodeFilter();
        smmValidateCodeFilter.setAuthenticationFailureHandler(authenticationFailureHandler);
        smmValidateCodeFilter.setSecurityProperties(securityProperties);
        smmValidateCodeFilter.afterPropertiesSet();

        http
                .addFilterBefore(smmValidateCodeFilter,UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .formLogin()
                .loginPage("/authenticaiton/require")
                .loginProcessingUrl("/authenticaiton/form")
                .successHandler(authenticationSuccessHandler)
                .failureHandler(authenticationFailureHandler)
                .and()
                .rememberMe()
                .tokenRepository(persistentTokenRepository())
                .tokenValiditySeconds(securityProperties.getBrower().getRememberMeSeconds())
                .userDetailsService(userDetailsService)
                .and()
                .authorizeRequests()
                .antMatchers("/authenticaiton/require",securityProperties.getBrower().getLoginPage(),"/code/*","/authentication/mobile").permitAll()
                .anyRequest()
                .authenticated()
        .and().csrf().disable().apply(smsCodeAuthenticationSecurityConfig);

        //自定义授权
//        authorizeConfigManger.config(http.authorizeRequests());
    }
}
