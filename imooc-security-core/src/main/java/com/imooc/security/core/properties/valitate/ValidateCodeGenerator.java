package com.imooc.security.core.properties.valitate;


import com.imooc.security.core.properties.valitate.code.ImageCode;
import com.imooc.security.core.properties.valitate.code.ValidateCode;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 *验证码逻辑生成器
 */
public interface ValidateCodeGenerator {
    ValidateCode creadeImageCode(HttpServletRequest request);
}
