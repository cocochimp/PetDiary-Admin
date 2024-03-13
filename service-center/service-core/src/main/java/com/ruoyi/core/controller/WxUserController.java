package com.ruoyi.core.controller;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.service.WxUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 小程序——"用户登录"模块
 *
 * @author cocochimp
 * @date 2024-03-12
 */
@RestController
@RequestMapping("/wx/user")
public class WxUserController extends BaseController {

    @Autowired
    private WxUserService wxUserService;

    /**
     * 微信用户登录详情
     */
    @PostMapping("/login")
    @ResponseBody
    @Operation(summary = "微信用户登录详情", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult login(@RequestParam(value = "code", required = false) String code,
                                   @RequestParam(value = "rawData", required = false) String rawData,
                                   @RequestParam(value = "signature", required = false) String signature){
        return wxUserService.login(code,rawData,signature);
    }

    /**
     * 修改用户信息
     */
    @PostMapping("/updateInfo")
    @ResponseBody
    @Operation(summary = "修改用户信息", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult updateInfo(@RequestBody UserInfo UserInfo){
        return wxUserService.updateInfo(UserInfo);
    }
}








