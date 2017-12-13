package com.imooc.security.core.properties.valitate.code.exception;

import org.springframework.security.core.AuthenticationException;

public class ValidateCodeException extends AuthenticationException{
    public ValidateCodeException(String msg) {
        super(msg);
    }
}
