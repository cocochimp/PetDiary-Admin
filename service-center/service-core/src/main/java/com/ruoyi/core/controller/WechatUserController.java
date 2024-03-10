package com.ruoyi.core.controller;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.service.IUserInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    private IUserInfoService iUserInfoService;

    /**
     * 微信用户登录详情
     */
    @PostMapping("wx/login")
    @ResponseBody
    @Operation(summary = "微信用户登录详情", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult login(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "rawData", required = false) String rawData,
                                   @RequestParam(value = "signature", required = false) String signature){
    //@RequestParam(value = "encrypteData", required = false) String encrypteData,
    //@RequestParam(value = "iv", required = false) String iv
        return iUserInfoService.login(code,rawData,signature);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("updateInfo")
    @ResponseBody
    @Operation(summary = "修改用户信息", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult updateInfo(@RequestBody UserInfo UserInfo){

        return iUserInfoService.updateInfo(UserInfo);
    }
}








