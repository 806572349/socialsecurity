package com.imooc.security.core.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.social.SocialProperties;

@Data
public class QQProperty extends SocialProperties{
    private String providerId="qq";


}
