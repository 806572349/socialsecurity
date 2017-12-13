package com.imooc.security.brower.handler;

import com.imooc.security.core.properties.LoginType;
import com.imooc.security.core.properties.SecurityProperties;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//登录成功就会被调用
@Component("ImoocAuthenticationSuccessHandler")
public class ImoocAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler //默认的成功处理器
//        implements AuthenticationSuccessHandler
{
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());
      private ObjectMapper objectMapper=new ObjectMapper();
      @Autowired
      SecurityProperties securityProperties;
    /**
     *
     * @param request
     * @param response
     * @param authentication 封装了认证信息，发起请求，session ，userdetatile等信息
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
            logger.info("登录成功");

        if (LoginType.JSON.equals(securityProperties.getBrower().getLoginPage())){
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(objectMapper.writeValueAsString(authentication));
        }else {
            super.onAuthenticationSuccess(request,response,authentication);

        }



    }
}
