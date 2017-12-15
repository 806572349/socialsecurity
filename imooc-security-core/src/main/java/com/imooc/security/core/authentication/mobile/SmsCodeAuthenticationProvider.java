package com.imooc.security.core.authentication.mobile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsCodeAuthenticationProvider implements AuthenticationProvider{
    private UserDetailsService userDetailsService;
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsCodeAuthenticationToken smsCodeAuthenticationToken=(SmsCodeAuthenticationToken)authentication;
        String mobile = (String) smsCodeAuthenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        if (userDetails==null){
            logger.warn("无法获取用户信息");
            throw  new InternalAuthenticationServiceException("无法获取用户信息");
        }
        SmsCodeAuthenticationToken token=new SmsCodeAuthenticationToken(userDetails,userDetails.getAuthorities());
        token.setDetails(smsCodeAuthenticationToken.getDetails());

        return token;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
