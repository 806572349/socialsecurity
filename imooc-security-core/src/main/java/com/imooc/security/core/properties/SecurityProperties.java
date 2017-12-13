package com.imooc.security.core.properties;

import com.imooc.security.core.properties.valitate.ValidateCodeProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "imooc.security")
public class SecurityProperties {
    private  BrowerProperties brower=new BrowerProperties();
    private ValidateCodeProperties code=new ValidateCodeProperties();

    public ValidateCodeProperties getCode() {
        return code;
    }

    public void setCode(ValidateCodeProperties code) {
        this.code = code;
    }

    public BrowerProperties getBrower() {
        return brower;
    }

    public void setBrower(BrowerProperties brower) {
        this.brower = brower;
    }
}
