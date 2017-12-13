package com.imooc.security.core.properties.valitate.processor;

import com.imooc.security.core.properties.valitate.code.ImageCode;
import com.imooc.security.core.properties.valitate.code.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;

@Component("imageValidateCodeProcessor")
public class ImageCodeProcessor extends AbstractValidateCodeProcessor<ImageCode> {


    @Override
    protected void send(ServletWebRequest request, ImageCode validateCode) throws Exception {
        ImageIO.write(validateCode.getImage(), "JPEG", request.getResponse().getOutputStream());
    }
}
