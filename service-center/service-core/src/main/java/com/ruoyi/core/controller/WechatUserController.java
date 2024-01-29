package com.ruoyi.core.controller;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.core.domain.WechatUserInfo;
import com.ruoyi.core.service.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lastwhisper
 * @desc
 * @email gaojun56@163.com
 */
@Controller
public class WechatUserController {

    @Autowired
    private WechatUserService wechatUserService;

    /**
     * 微信用户登录详情
     */
    @PostMapping("wx/login")
    @ResponseBody
    public GlobalResult login(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "rawData", required = false) String rawData,
                                   @RequestParam(value = "signature", required = false) String signature){
    //@RequestParam(value = "encrypteData", required = false) String encrypteData,
    //@RequestParam(value = "iv", required = false) String iv
        return wechatUserService.login(code,rawData,signature);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("updateInfo")
    @ResponseBody
    public GlobalResult updateInfo(@RequestBody WechatUserInfo wechatUserInfo){

        return wechatUserService.updateInfo(wechatUserInfo);
    }
}








