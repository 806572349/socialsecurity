/**
 * 
 */
package com.imooc.security.core.properties.valitate;

import com.imooc.security.core.properties.valitate.code.ValidateCode;
import com.imooc.security.core.properties.valitate.code.ValidateCodeType;
import org.springframework.web.context.request.ServletWebRequest;

/**
 * 校验码存取器
 * 
 * @author zhailiang
 *
 */
public interface ValidateCodeRepository {

	/**
	 * 保存验证码
	 * @param request
	 * @param code
	 * @param validateCodeType
	 */
	void save(ServletWebRequest request, ValidateCode code, String validateCodeType);
	/**
	 * 获取验证码
	 * @param request
	 * @param validateCodeType
	 * @return
	 */
	ValidateCode get(ServletWebRequest request, String validateCodeType);
	/**
	 * 移除验证码
	 * @param request
	 * @param codeType
	 */
	void remove(ServletWebRequest request, String codeType);

}
