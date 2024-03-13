package com.ruoyi.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.mapper.WxUserMapper;
import com.ruoyi.core.service.WxUserService;
import com.ruoyi.system.api.RemoteFileService;
import com.ruoyi.system.api.domain.SysFile;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * 小程序——"用户登录"模块
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class WxUserServiceImpl implements WxUserService
{

    @Autowired
    private WxUserMapper wxUserMapper;

    @Autowired
    private RemoteFileService remoteFileService;

    @Override
    public GlobalResult login(String code, String rawData, String signature) {
        // 用户非敏感信息：rawData
        // 签名：signature
        JSONObject rawDataJson = JSON.parseObject(rawData);
        // 1.接收小程序发送的code
        // 2.开发者服务器 登录凭证校验接口 appi + appsecret + code
        com.alibaba.fastjson2.JSONObject SessionKeyOpenId = com.ruoyi.common.core.utils.wechat.WechatUtil.getSessionKeyOrOpenId(code);
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
        UserInfo userInfo = wxUserMapper.selectUserInfoByUserId(openid);
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (userInfo == null) {
            //将头像上传到oss
            String avatarUrl = rawDataJson.getString("avatarUrl");
            SysFile fileResult = uploadAvatarUrl(avatarUrl);

            // 用户信息入库
            userInfo = new UserInfo();
            userInfo.setOpenid(openid);
            userInfo.setSessionKey(skey);
            userInfo.setCreateTime(new Date());
            userInfo.setUpdateTime(new Date());
            userInfo.setSessionKey(sessionKey);
            userInfo.setBrief("hello world!");
            userInfo.setAvatar(fileResult.getUrl());
            userInfo.setGender(Integer.parseInt(rawDataJson.getString("gender")));
            userInfo.setNickname(getStringRandom(8));

            wxUserMapper.insertUserInfo(userInfo);
        } else {
            // 已存在，更新用户登录时间
            userInfo.setUpdateTime(new Date());
            // 重新设置会话skey
            userInfo.setSessionKey(skey);
            wxUserMapper.updateUserInfo(userInfo);
        }
        //encrypteData比rowData多了appid和openid
        //6. 把新的skey返回给小程序
        GlobalResult result = null;
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            result = GlobalResult.build(200, null, objectMapper.writeValueAsString(userInfo));
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public GlobalResult updateInfo(UserInfo userInfo) {
        UserInfo user = wxUserMapper.selectUserInfoByUserId(userInfo.getOpenid());
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (user != null) {
//            SysFile fileResult = uploadAvatarUrl(userInfo.getAvatar());

            //不为null的话则修改，否则不做操作
            user.setAvatar(userInfo.getAvatar() != null ? userInfo.getAvatar() : user.getAvatar());
            user.setNickname(userInfo.getNickname() != null ? userInfo.getNickname() : user.getNickname());
            user.setGender(userInfo.getGender() != null ? userInfo.getGender() : user.getGender());
            user.setBrief(userInfo.getBrief() != null ? userInfo.getBrief() : user.getBrief());
            user.setUpdateTime(new Date());
            user.setSessionKey(skey);
            wxUserMapper.updateUserInfo(user);
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

    public SysFile uploadAvatarUrl(String avatarUrl){
        R<SysFile> fileResult = null;
        try {
            // 打开图像文件链接的输入流
            InputStream inputStream = new URL(avatarUrl).openStream();
            // 创建临时文件
            File tempFile = File.createTempFile("avatar", ".jpg");

            // 将输入流的数据保存到临时文件
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            byte[] buffer = new byte[8192];
            int length;
            while ((length = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }

            // 关闭输入流和输出流
            inputStream.close();
            outputStream.close();

            // 将临时文件转换为MultipartFile类型文件
            MultipartFile multipartFile = new MockMultipartFile("file", tempFile.getName(), MediaType.IMAGE_JPEG_VALUE, new FileInputStream(tempFile));

            // 上传MultipartFile类型文件到OSS中
            fileResult = remoteFileService.upload(multipartFile);

            // 删除临时文件
            tempFile.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert fileResult != null;
        return fileResult.getData();
    }
}
