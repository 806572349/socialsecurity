/**
 * 
 */
package com.imooc.security.app.authentication.impl;
import org.springframework.data.redis.core.RedisTemplate;
import java.util.concurrent.TimeUnit;

import com.imooc.security.core.properties.valitate.ValidateCodeRepository;
import com.imooc.security.core.properties.valitate.code.ValidateCode;
import com.imooc.security.core.properties.valitate.code.ValidateCodeType;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.annotation.Resource;


/**
 * 基于redis的验证码存取器，避免由于没有session导致无法存取验证码的问题
 * 
 * @author zhailiang
 *
 */
@Component
public class RedisValidateCodeRepository implements ValidateCodeRepository {

	 //日志
	 private Logger logger= LoggerFactory.getLogger(getClass());
	@Autowired
	 private RedisTemplate<Object, Object> redisTemplate;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeRepository#save(org.
	 * springframework.web.context.request.ServletWebRequest,
	 * com.imooc.security.core.validate.code.ValidateCode,
	 * com.imooc.security.core.validate.code.ValidateCodeType)
	 */
	@Override
	public void save(ServletWebRequest request, ValidateCode code, String type) {
		String buildKey = buildKey(request, type);
		logger.info("savebuildKey:"+buildKey);
		ValidateCode validateCode = new ValidateCode(code.getCode(), code.getExpireTime());
		redisTemplate.opsForValue().set(buildKey, validateCode, 30, TimeUnit.MINUTES);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeRepository#get(org.
	 * springframework.web.context.request.ServletWebRequest,
	 * com.imooc.security.core.validate.code.ValidateCodeType)
	 */
	@Override
	public ValidateCode get(ServletWebRequest request, String type) {
		String buildKey = buildKey(request, type);
		logger.info("getbuildKey:"+buildKey);

		if (redisTemplate==null)
		{

			logger.info("redisTemplate为空");

		}
		Object value = redisTemplate.opsForValue().get(buildKey);
		if (value == null) {

            logger.info("value为空");
			return null;
		}
		return (ValidateCode) value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.imooc.security.core.validate.code.ValidateCodeRepository#remove(org.
	 * springframework.web.context.request.ServletWebRequest,
	 * com.imooc.security.core.validate.code.ValidateCodeType)
	 */
	@Override
	public void remove(ServletWebRequest request, String type) {
		redisTemplate.delete(buildKey(request, type));
	}

	/**
	 * @param request
	 * @param type
	 * @return
	 */
	private String buildKey(ServletWebRequest request, String type) {
		String deviceId = request.getHeader("deviceId");
		if (StringUtils.isBlank(deviceId)) {
			throw new RuntimeException("请在请求头中携带deviceId参数");
		}
		return "code:" + type.toString() + ":" + deviceId;
	}

}
