//package com.ruoyi.common.core.constant;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Component
//public class SessionInfo {
//
//    private static String appid;
//    private static String secret;
//
//    @Value("${weixin.appid}")
//    public void setAppid(String appid) {
//        SessionInfo.appid = appid;
//    }
//
//    @Value("${weixin.secret}")
//    public void setSecret(String secret) {
//        SessionInfo.secret = secret;
//    }
//
//    public static String getAppid() {
//        return appid;
//    }
//
//    public static String getSecret() {
//        return secret;
//    }
//}