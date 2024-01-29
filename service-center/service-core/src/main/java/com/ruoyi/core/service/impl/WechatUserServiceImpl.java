package com.ruoyi.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.common.core.utils.wechat.WechatUtil;
import com.ruoyi.core.domain.WechatUserInfo;
import com.ruoyi.core.mapper.WechatUserInfoMapper;
import com.ruoyi.core.service.WechatUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Random;
import java.util.UUID;


/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author crush
 * @since 2021-09-14
 */
@Slf4j
@Service
public class WechatUserServiceImpl implements WechatUserService {

//    @Autowired
//    private WechatUserMapper wechatUserMapper;

    @Autowired
    private WechatUserInfoMapper wechatUserInfoMapper;

    @Override
    public GlobalResult login(String code, String rawData, String signature) {
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        com.alibaba.fastjson2.JSONObject SessionKeyOpenId = WechatUtil.getSessionKeyOrOpenId(code);
        // 3.接收微信接口服务 获取返回的参数
        String openid = SessionKeyOpenId.getString("openid");
        String sessionKey = SessionKeyOpenId.getString("session_key");

        // 4.校验签名 小程序发送的签名signature与服务器端生成的签名signature2 = sha1(rawData + sessionKey)
        String signature2 = DigestUtils.sha1Hex(rawData + sessionKey);
        if (!signature.equals(signature2)) {
            return GlobalResult.build(500, "签名校验失败", null);
        }
        // 5.根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；不是的话，更新最新登录时间
//        WechatUserInfo wechatUserInfo = this.wechatUserMapper.selectById(openid);
        WechatUserInfo wechatUserInfo = wechatUserInfoMapper.getWechatUserInfoByOpenId(openid);
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (wechatUserInfo == null) {
            // 用户信息入库
            wechatUserInfo = new WechatUserInfo();
            wechatUserInfo.setOpenId(openid);
            wechatUserInfo.setSkey(skey);
            wechatUserInfo.setCreateTime(new Date());
            wechatUserInfo.setLastVisitTime(new Date());
            wechatUserInfo.setSessionKey(sessionKey);
            wechatUserInfo.setSignature("hello world!");
            wechatUserInfo.setAvatarUrl(rawDataJson.getString("avatarUrl"));
            wechatUserInfo.setGender(Integer.parseInt(rawDataJson.getString("gender")));
            wechatUserInfo.setNickName(getStringRandom(16));

            wechatUserInfoMapper.insertWechatUserInfo(wechatUserInfo);
        } else {
            // 已存在，更新用户登录时间
            wechatUserInfo.setLastVisitTime(new Date());
            // 重新设置会话skey
            wechatUserInfo.setSkey(skey);
            wechatUserInfoMapper.updateWechatUserInfo(wechatUserInfo);
        }
        //encrypteData比rowData多了appid和openid
        //6. 把新的skey返回给小程序
        GlobalResult result = null;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            result = GlobalResult.build(200, null, objectMapper.writeValueAsString(wechatUserInfo));
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public GlobalResult updateInfo(WechatUserInfo userInfo) {
        WechatUserInfo user = wechatUserInfoMapper.getWechatUserInfoByOpenId(userInfo.getOpenId());
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (user != null) {
            //不为null的话则修改，否则不做操作
            user.setAvatarUrl(userInfo.getAvatarUrl() != null ? userInfo.getAvatarUrl() : user.getAvatarUrl());
            user.setNickName(userInfo.getNickName() != null ? userInfo.getNickName() : user.getNickName());
            user.setGender(userInfo.getGender() != null ? userInfo.getGender() : user.getGender());
            user.setSignature(userInfo.getSignature() != null ? userInfo.getSignature() : user.getSignature());
            user.setLastVisitTime(new Date());
            user.setSkey(skey);
            wechatUserInfoMapper.updateWechatUserInfo(user);
        }
        // 把新的skey返回给小程序
        GlobalResult result = null;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            result = GlobalResult.build(200, null, objectMapper.writeValueAsString(user));
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    //生成随机用户名，数字和字母组成,
    public String getStringRandom(int length) {

        StringBuilder val = new StringBuilder();
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val.append((char) (random.nextInt(26) + temp));
            } else {
                val.append(random.nextInt(10));
            }
        }
        return val.toString();
    }

}