package com.imooc.security.brower;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailService implements UserDetailsService {
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());
      @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户名"+username);

        logger.info("密码"+passwordEncoder.encode("123123"));
        return new User(username,passwordEncoder.encode("123123"),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("admin"));
    }
}
