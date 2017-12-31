package com.imooc.security.core.properties;

import com.imooc.security.core.properties.valitate.ValidateCodeProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "imooc.security")
@Data
public class SecurityProperties {
    private  BrowerProperties brower=new BrowerProperties();
    private ValidateCodeProperties code=new ValidateCodeProperties();

    private Oauth2Porperties oauth2=new Oauth2Porperties();

    private SocialProperty social=new SocialProperty();

}
