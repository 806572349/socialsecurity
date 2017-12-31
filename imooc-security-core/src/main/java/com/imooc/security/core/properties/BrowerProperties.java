package com.imooc.security.core.properties;

import lombok.Data;

@Data
public class BrowerProperties {
    private  String loginPage="/signIn.html";

    private String signUp="/signUp.html";

    private  LoginType loginType=LoginType.JSON;


    private  int rememberMeSeconds=3600;



}
