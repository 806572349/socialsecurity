package com.imooc.security.web.controller;

import com.imooc.security.core.properties.SecurityProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class UserController {
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());

    @Autowired
    SecurityProperties securityProperties;
    @GetMapping("/user")
    public String user(HttpServletRequest request) throws UnsupportedEncodingException {
        String header = request.getHeader("Authorization");

        String token = StringUtils.substringAfter(header, "bearer ");
        Claims claims = Jwts.parser().setSigningKey(securityProperties.getOauth2().getJwtkey().getBytes("UTF-8"))
                .parseClaimsJws(token).getBody();
        String csn = (String) claims.get("csn");
        logger.info(csn);
        return "hello";
    }
}
