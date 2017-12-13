package com.imooc.security.core.properties.valitate.Impl;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.properties.valitate.ValidateCodeGenerator;
import com.imooc.security.core.properties.valitate.code.ImageCode;
import com.imooc.security.core.properties.valitate.code.ValidateCode;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

@Component("smsCodeGenerator")
public class SmsCodeGenerator implements ValidateCodeGenerator {
    @Autowired
    private SecurityProperties securityProperties;
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());


    @Override
    public ValidateCode creadeImageCode(HttpServletRequest request) {
        String random = RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLength());
        logger.info("random:"+random);
        return  new ValidateCode(random,securityProperties.getCode().getSms().getExpireIn());

    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

}
