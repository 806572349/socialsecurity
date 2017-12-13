package com.imooc.security.core.properties.valitate.processor;

import com.imooc.security.core.properties.valitate.ValidateCodeGenerator;
import com.imooc.security.core.properties.valitate.ValidateCodeProcessor;
import com.imooc.security.core.properties.valitate.code.ValidateCode;
import com.imooc.security.core.properties.valitate.code.ValidateCodeType;
import com.imooc.security.core.properties.valitate.code.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class AbstractValidateCodeProcessor<C extends ValidateCode> implements ValidateCodeProcessor {
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());
      //ValidateCodeProcessor
    @Autowired
    private Map<String,ValidateCodeGenerator> validateCodeGenerators;
    @Override
    public void create(ServletWebRequest request) throws Exception {
        C validateCode = generate(request);
        save(request, validateCode);
        send(request, validateCode);
    }

    /**
     * 生成校验码
     *
     * @param request
     * @return
     */
    @SuppressWarnings("unchecked")
    private C generate(ServletWebRequest request) {
        String type = getProcessorTyper(request).toString().toLowerCase();
        String generatorName = type + ValidateCodeGenerator.class.getSimpleName();
//        logger.info("type:"+type+"generatorName:"+generatorName);
        ValidateCodeGenerator validateCodeGenerator = validateCodeGenerators.get(generatorName);
        Set<String> keySet = validateCodeGenerators.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            logger.info(next);
//            "imageValidateCodeProcessor"
        }

        if (validateCodeGenerator == null) {
            logger.warn("验证码生成器"+generatorName+"不存在");
            throw new ValidateCodeException("验证码生成器" + generatorName + "不存在");
        }
        return (C) validateCodeGenerator.creadeImageCode(request.getRequest());
    }
    /**
     * 保存校验码
     *
     * @param request
     * @param validateCode
     */
    private void save(ServletWebRequest request, C validateCode) {
        ValidateCode code = new ValidateCode(validateCode.getCode(), validateCode.getExpireTime());
        sessionStrategy.setAttribute(request,session_key,code);
    }
    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        logger.info("ValidateCodeType:"+type);
        return ValidateCodeType.valueOf(type.toUpperCase());
    }
    /**
     * 发送校验码，由子类实现
     *
     * @param request
     * @param validateCode
     * @throws Exception
     */
    protected abstract void send(ServletWebRequest request, C validateCode) throws Exception;

    private String getProcessorTyper(ServletWebRequest request){
        return StringUtils.substringAfter(request.getRequest().getRequestURI(),"/code/");
    }
}
