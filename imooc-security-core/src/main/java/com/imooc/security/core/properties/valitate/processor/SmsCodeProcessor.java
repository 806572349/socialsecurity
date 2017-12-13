package com.imooc.security.core.properties.valitate.processor;

import com.imooc.security.core.properties.valitate.ISmsCodeSender;
import com.imooc.security.core.properties.valitate.code.ValidateCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import sun.security.util.SecurityConstants;

@Component("smsValidateCodeProcessor")
public class SmsCodeProcessor extends  AbstractValidateCodeProcessor<ValidateCode> {
    @Autowired
    ISmsCodeSender smsCodeSender;

    @Override
    protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {

        String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
        smsCodeSender.send(mobile, validateCode.getCode());
    }
}
