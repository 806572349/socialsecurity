package com.imooc.security.core.properties.valitate.filter;

import com.imooc.security.core.properties.SecurityProperties;
import com.imooc.security.core.properties.valitate.ValidateCodeRepository;
import com.imooc.security.core.properties.valitate.code.ValidateCode;
import com.imooc.security.core.properties.valitate.code.ValidateCodeType;
import com.imooc.security.core.properties.valitate.code.controller.ValidateCodeContoller;
import com.imooc.security.core.properties.valitate.code.exception.ValidateCodeException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static com.imooc.security.core.properties.valitate.ValidateCodeProcessor.session_key;

//InitializingBean 在初始化完毕之后
public class SmmValidateCodeFilter extends OncePerRequestFilter implements InitializingBean{
    @Autowired
    AuthenticationFailureHandler authenticationFailureHandler;
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    private Set<String> urls=new HashSet<>();
    private SecurityProperties securityProperties;
    private AntPathMatcher pathMatcher=new AntPathMatcher();
//    @Autowired
//    private ValidateCodeRepository redisValidateCodeRepository;
    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());

    @Override
    public void afterPropertiesSet() throws ServletException {
        super.afterPropertiesSet();
        String[] configUrls=StringUtils.splitByWholeSeparatorPreserveAllTokens(securityProperties.getCode().getSms().getUrl(),",");
        for (String url:
                configUrls) {
            urls.add(url);

        }
        urls.add("/authentication/mobile");

    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
           boolean action=false;

        for (String url:
                urls) {
            if (pathMatcher.match(url,request.getRequestURI())){
                action=true;
            }
        }
            if (action){
                    try {
//                        validate(new ServletWebRequest(request));
                    }catch (ValidateCodeException ex){
                        authenticationFailureHandler.onAuthenticationFailure(request,response,ex);
                        return;
                    }

            }
            filterChain.doFilter(request,response);
    }
    //校验验证码
    /*private void validate(ServletWebRequest request) throws ServletRequestBindingException {
        ValidateCode imageCode = (ValidateCode) sessionStrategy.getAttribute(request, ValidateCodeContoller.session_key+"SMS");
//        session_keySMS
        if (redisTemplate==null){
            logger.warn("redisTemplate为空");
        }

        if (redisValidateCodeRepository==null){
            logger.warn("redisValidateCodeRepository为空");
//            redisValidateCodeRepository=new RedisValidateCodeRepository();
//            throw new  RuntimeException("redisValidateCodeRepository为空");
        }

//        ValidateCode imageCode = redisValidateCodeRepository.get(request, session_key+"SMS");
        String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "smsCode");
        logger.info("codeInRequest:"+codeInRequest);
        if (StringUtils.isBlank(codeInRequest)){
            logger.info("验证码不能空");
            throw new ValidateCodeException("验证码不能空");
        }
        if (imageCode==null){
            logger.info("验证码不存在");
            throw new ValidateCodeException("验证码不存在");

        }
        if (imageCode.isExpried()){
            logger.info("验证码过期额");
//            sessionStrategy.removeAttribute(request,ValidateCodeContoller.session_key+"SMS");
            redisValidateCodeRepository.remove(request,session_key+"SMS");
            throw new ValidateCodeException("验证码过期额");
        }
        if (!StringUtils.equals(imageCode.getCode(),codeInRequest)){
            logger.info("验证码不匹配");
            throw  new ValidateCodeException("验证码不匹配");
        }

        sessionStrategy.removeAttribute(request,ValidateCodeContoller.session_key+"SMS");
//        redisValidateCodeRepository.remove(request,session_key+"SMS");
    }*/

    private ValidateCodeType getValidateCodeType(ServletWebRequest request) {
        String type = StringUtils.substringBefore(getClass().getSimpleName(), "CodeProcessor");
        logger.info("ValidateCodeType:"+type);
        return ValidateCodeType.valueOf(type.toUpperCase());
    }


    private String getSessionKey(ServletWebRequest request) {
        return session_key + getValidateCodeType(request).toString().toUpperCase();
    }
    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }

    public AuthenticationFailureHandler getAuthenticationFailureHandler() {
        return authenticationFailureHandler;
    }

    public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationFailureHandler = authenticationFailureHandler;
    }
}
