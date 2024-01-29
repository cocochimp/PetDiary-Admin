package com.ruoyi.core.mapper;// WechatUserInfoMapper.java

import com.ruoyi.core.domain.WechatUserInfo;

import java.util.List;

/**
 * 视频管理Mapper接口
 *
 * @author cocochimp
 * @date 2023-12-04
 */
public interface WechatUserInfoMapper {

    void insertWechatUserInfo(WechatUserInfo wechatUserInfo);

    void updateWechatUserInfo(WechatUserInfo wechatUserInfo);

    void deleteWechatUserInfo(String openId);

    WechatUserInfo getWechatUserInfoByOpenId(String openId);

    List<WechatUserInfo> getAllWechatUserInfo();

}