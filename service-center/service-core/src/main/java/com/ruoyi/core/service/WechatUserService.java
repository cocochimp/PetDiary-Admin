package com.ruoyi.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
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
public interface WechatUserService extends IService<WechatUserInfo> {
    GlobalResult login(String code, String rawData, String signature);

    GlobalResult updateInfo(WechatUserInfo wechatUserInfo);
}
