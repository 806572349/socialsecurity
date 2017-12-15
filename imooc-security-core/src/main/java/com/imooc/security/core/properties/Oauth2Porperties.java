package com.imooc.security.core.properties;

public class Oauth2Porperties {
    private  Oauth2ClientProperties[] clients={};
    private  String jwtkey="imooc";

    public String getJwtkey() {
        return jwtkey;
    }

    public void setJwtkey(String jwtkey) {
        this.jwtkey = jwtkey;
    }

    public Oauth2ClientProperties[] getClients() {
        return clients;
    }

    public void setClients(Oauth2ClientProperties[] clients) {
        this.clients = clients;
    }
}
