package com.ruoyi.core.service;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.core.domain.WechatUserInfo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author crush
 * @since 2021-09-14
 */
public interface WechatUserService {
    GlobalResult login(String code, String rawData, String signature);

    GlobalResult updateInfo(WechatUserInfo wechatUserInfo);
}
