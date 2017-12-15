package com.imooc.security.app.authentication.handler;

import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component("ImoocAuthenticationFailureHandler")
public class ImoocAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler//默认失败处理器
//        implements AuthenticationFailureHandler
{
    //日志
    private Logger logger= LoggerFactory.getLogger(getClass());
    private ObjectMapper objectMapper=new ObjectMapper();
    @Autowired
    SecurityProperties securityProperties;
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        logger.info("登录失败");
        if (LoginType.JSON.equals(securityProperties.getBrower().getLoginType())){
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.setContentType("application/json;charset=utf-8");
            ModelMap modelMap = new ModelMap();
            modelMap.put("code",1);
            modelMap.put("msg",exception.getMessage());
            response.getWriter().write(objectMapper.writeValueAsString(modelMap));
        }else {
            super.onAuthenticationFailure(request,response,exception);

        }

    }
}
