package com.ruoyi.core.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.core.enums.DelFlag;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.mapper.UserInfoMapper;
import com.ruoyi.core.service.IUserInfoService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 用户列表Service业务层处理
 *
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService
{
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 查询用户列表
     *
     * @param userId 用户列表主键
     * @return 用户列表
     */
    @Override
    public UserInfo selectUserInfoByUserId(String userId)
    {
        return userInfoMapper.selectUserInfoByUserId(userId);
    }

    /**
     * 查询用户列表列表
     *
     * @param userInfo 用户列表
     * @return 用户列表
     */
    @Override
    public List<UserInfo> selectUserInfoList(UserInfo userInfo)
    {
        List<UserInfo> userInfos = userInfoMapper.selectUserInfoList(userInfo);
        userInfos.removeIf(i -> Objects.equals(i.getDelFlag(), DelFlag.DELETE.getCode()));
        return userInfos;
    }

    /**
     * 新增用户列表
     *
     * @param userInfo 用户列表
     * @return 结果
     */
    @Override
    public int insertUserInfo(UserInfo userInfo)
    {
        userInfo.setCreateTime(DateUtils.getNowDate());
        return userInfoMapper.insertUserInfo(userInfo);
    }

    /**
     * 修改用户列表
     *
     * @param userInfo 用户列表
     * @return 结果
     */
    @Override
    public int updateUserInfo(UserInfo userInfo)
    {
        userInfo.setUpdateTime(DateUtils.getNowDate());
        return userInfoMapper.updateUserInfo(userInfo);
    }

    /**
     * 批量删除用户列表
     *
     * @param userIds 需要删除的用户列表主键
     * @return 结果
     */
    @Override
    public int deleteUserInfoByUserIds(String[] userIds)
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setDelFlag(DelFlag.DELETE.getCode());
        int row = 0;
        for (String userId : userIds) {
            userInfo.setOpenid(userId);
            row += userInfoMapper.updateUserInfo(userInfo);
        }
        return row;
    }

    /**
     * 删除用户列表信息
     *
     * @param userId 用户列表主键
     * @return 结果
     */
    @Override
    public int deleteUserInfoByUserId(Long userId)
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenid(String.valueOf(userId));
        userInfo.setDelFlag(DelFlag.DELETE.getCode());
        return userInfoMapper.updateUserInfo(userInfo);
    }

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
        UserInfo userInfo = userInfoMapper.selectUserInfoByUserId(openid);
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (userInfo == null) {
            // 用户信息入库
            userInfo = new UserInfo();
            userInfo.setOpenid(openid);
            userInfo.setSessionKey(skey);
            userInfo.setCreateTime(new Date());
            userInfo.setUpdateTime(new Date());
            userInfo.setSessionKey(sessionKey);
            userInfo.setBrief("hello world!");
            userInfo.setAvatar(rawDataJson.getString("avatarUrl"));
            userInfo.setGender(Integer.parseInt(rawDataJson.getString("gender")));
            userInfo.setNickname(getStringRandom(8));

            userInfoMapper.insertUserInfo(userInfo);
        } else {
            // 已存在，更新用户登录时间
            userInfo.setUpdateTime(new Date());
            // 重新设置会话skey
            userInfo.setSessionKey(skey);
            userInfoMapper.updateUserInfo(userInfo);
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
        UserInfo user = userInfoMapper.selectUserInfoByUserId(userInfo.getOpenid());
        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if (user != null) {
            //不为null的话则修改，否则不做操作
            user.setAvatar(userInfo.getAvatar() != null ? userInfo.getAvatar() : user.getAvatar());
            user.setNickname(userInfo.getNickname() != null ? userInfo.getNickname() : user.getNickname());
            user.setGender(userInfo.getGender() != null ? userInfo.getGender() : user.getGender());
            user.setBrief(userInfo.getBrief() != null ? userInfo.getBrief() : user.getBrief());
            user.setUpdateTime(new Date());
            user.setSessionKey(skey);
            userInfoMapper.updateUserInfo(user);
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
