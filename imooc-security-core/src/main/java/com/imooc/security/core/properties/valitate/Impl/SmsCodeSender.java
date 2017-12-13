package com.imooc.security.core.properties.valitate.Impl;

import com.imooc.security.core.properties.valitate.ISmsCodeSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SmsCodeSender implements ISmsCodeSender {
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public void send(String mobile, String code) {
        logger.info("向手机"+mobile+"发送验证码"+code);
    }
}
