package com.imooc.security.core.properties.valitate;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.properties.valitate.Impl.ImageCodeGenerator;
import com.imooc.security.core.properties.valitate.Impl.SmsCodeSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidateCodeBeanConfig {
    @Autowired
    SecurityProperties securityProperties;


    @Bean
    @ConditionalOnMissingBean(name = "imageCodeGenerator") //不存在这样的bean 才实现下面；
    public ValidateCodeGenerator validateCodeGenerator(){
        ImageCodeGenerator imageCodeGenerator = new ImageCodeGenerator();
        imageCodeGenerator.setSecurityProperties(securityProperties);
        return imageCodeGenerator;
    }

    @Bean
    @ConditionalOnMissingBean(ISmsCodeSender.class) //不存在这样的bean 才实现下面；
    public ISmsCodeSender SmsCodeSender(){
        return new SmsCodeSender();
    }


}
