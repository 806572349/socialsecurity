package com.imooc.security.web.security;

import com.imooc.security.web.api.IUserInterfaces;
import com.imooc.security.web.model.LaosijiUser;
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

import java.util.List;

/*@Component
public class MyUserDetailService implements UserDetailsService {
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());
      @Autowired
    PasswordEncoder passwordEncoder;

      @Autowired
    IUserInterfaces iUserInterfaces;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("用户名"+username);
       *//* List<LaosijiUser> laosijiUsers = iUserInterfaces.findByUsername(username);
        if (laosijiUsers.size()<=0){
                throw  new UsernameNotFoundException("没有该用户");
        }
        LaosijiUser laosijiUser = laosijiUsers.get(0);
//        logger.info("密码"+passwordEncoder.encode("123123"));
        logger.info(laosijiUser.getHasRole());*//*
        //,ROLE_USER
        return new User(username,passwordEncoder.encode("123456"),true,true,true,true, AuthorityUtils.commaSeparatedStringToAuthorityList("USER"));
    }
}*/
