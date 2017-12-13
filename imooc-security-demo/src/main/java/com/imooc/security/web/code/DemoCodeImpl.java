package com.imooc.security.web.code;

import com.imooc.security.core.properties.valitate.ValidateCodeGenerator;
import com.imooc.security.core.properties.valitate.code.ImageCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
//@Component("imageCodeGenerator")
public class DemoCodeImpl implements ValidateCodeGenerator {
     //日志
      private Logger logger= LoggerFactory.getLogger(getClass());
    @Override
    public ImageCode creadeImageCode(HttpServletRequest request) {
        logger.info("更加高级的图形验证吗");
        return null;
    }
}
