package com.imooc.security.core.properties;

import lombok.Data;

@Data
public class SocialProperty {
    QQProperty qq=new QQProperty();
    private String filterProcessUrl="/auth";

}
