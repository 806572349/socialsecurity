package com.imooc.security.core.properties.valitate;

import org.springframework.web.context.request.ServletWebRequest;

public interface ValidateCodeProcessor {
    /**
     * session 的前缀
     */
       String session_key="session_key";

    /**
     * 创建校验码
     * @param request
     * @throws Exception
     */
       void  create(ServletWebRequest request) throws  Exception;
}
