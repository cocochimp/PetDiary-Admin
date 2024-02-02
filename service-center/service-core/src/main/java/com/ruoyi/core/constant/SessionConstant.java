package com.ruoyi.core.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SessionConstant {

    private static String appid;
    private static String secret;

    @Value("${weixin.appid}")
    public void setAppid(String appid) {
        SessionConstant.appid = appid;
    }

    @Value("${weixin.secret}")
    public void setSecret(String secret) {
        SessionConstant.secret = secret;
    }

    public static String getAppid() {
        return appid;
    }

    public static String getSecret() {
        return secret;
    }
}