package com.ruoyi.core.service;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.core.domain.UserInfo;

/**
 * 小程序——"用户登录"模块
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface WxUserService
{
    /**
     * 删除用户列表信息
     *
     * @param code,rawData,signature 用户列表主键
     * @return 结果
     */
    GlobalResult login(String code, String rawData, String signature);

    /**
     * 删除用户列表信息
     *
     * @param userInfo 用户列表主键
     * @return 结果
     */
    GlobalResult updateInfo(UserInfo userInfo);
}
