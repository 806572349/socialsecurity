package com.imooc.security.core.properties;

public class BrowerProperties {
    private  String loginPage="/signIn.html";


    private  LoginType loginType=LoginType.JSON;


    private  int rememberMeSeconds=3600;

    public int getRememberMeSeconds() {
        return rememberMeSeconds;
    }

    public void setRememberMeSeconds(int rememberMeSeconds) {
        this.rememberMeSeconds = rememberMeSeconds;
    }

    public LoginType getLoginType() {
        return loginType;
    }

    public void setLoginType(LoginType loginType) {
        this.loginType = loginType;
    }

    public String getLoginPage() {
        return loginPage;
    }

    public void setLoginPage(String loginPage) {
        this.loginPage = loginPage;
    }
    // region
    /*
          ┏┛ ┻━━━━━┛ ┻┓
          ┃　　　　　　 ┃
          ┃　　　━　　　┃
          ┃　┳┛　  ┗┳　┃
          ┃　　　　　　 ┃
          ┃　　　┻　　　┃
          ┃　　　　　　 ┃
          ┗━┓　　　┏━━━┛
           ┃　　　┃   神兽保佑
           ┃　　　┃   代码无BUG！
           ┃　　　┗━━━━━━━━━┓
           ┃　　　　　　　    ┣┓
           ┃　　　　         ┏┛
           ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
             ┃ ┫ ┫   ┃ ┫ ┫
             ┗━┻━┛   ┗━┻━┛
     */
    // endregion
}
