package com.imooc.security.core.properties.valitate.code.controller;

import com.imooc.security.core.properties.valitate.ISmsCodeSender;
import com.imooc.security.core.properties.valitate.ValidateCodeGenerator;
import com.imooc.security.core.properties.valitate.ValidateCodeProcessor;
import com.imooc.security.core.properties.valitate.code.ImageCode;
import com.imooc.security.core.properties.valitate.code.ValidateCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;
import sun.security.util.SecurityConstants;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@RestController
public class ValidateCodeContoller {
    private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
    public static final String session_key="session_key";
    private Logger logger= LoggerFactory.getLogger(getClass());
  /* @Autowired
   @Qualifier("imageCodeGenerator")
    private ValidateCodeGenerator imageValidateCodeGenerator;

    @Autowired
    @Qualifier("smsCodeGenerator")
    private ValidateCodeGenerator smsValidateCodeGenerator;



    @Autowired
    private ISmsCodeSender smsCodeGenerator;
    @GetMapping("/code/image")
    public  void creadeCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ImageCode imageCode=(ImageCode)imageValidateCodeGenerator.creadeImageCode(request);
        sessionStrategy.setAttribute(new ServletWebRequest(request),session_key,imageCode);
        ImageIO.write(imageCode.getImage(),"JPEG",response.getOutputStream());
    }
    @GetMapping("/code/sms")
    public  void creadeSms(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletRequestBindingException {
        ValidateCode code=smsValidateCodeGenerator.creadeImageCode(request);
        String mobile = ServletRequestUtils.getRequiredStringParameter(request, "mobile");
        smsCodeGenerator.send(mobile,code.getCode());
        sessionStrategy.setAttribute(new ServletWebRequest(request),session_key,code);

    }*/
     //日志

    @Autowired
    private Map<String, ValidateCodeProcessor> validateCodeProcessors;


    @GetMapping("/code" + "/{type}")
    public void createCode(HttpServletRequest request, HttpServletResponse response, @PathVariable String type)
            throws Exception {
        Set<String> keySet = validateCodeProcessors.keySet();
        Iterator<String> iterator = keySet.iterator();
        while (iterator.hasNext()){
            String next = iterator.next();
            logger.info(next);
//            "imageValidateCodeProcessor"
        }
        String name = type.toLowerCase() + ValidateCodeProcessor.class.getSimpleName();
        ValidateCodeProcessor imageValidateCodeProcessor = validateCodeProcessors.get(name);
        if (imageValidateCodeProcessor==null){
            logger.warn("为空");
        }
        imageValidateCodeProcessor.create(new ServletWebRequest(request,response));
    }

}
