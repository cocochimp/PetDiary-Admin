package com.ruoyi.core.controller;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.service.WxSocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 小程序——社区
 *
 * @author cocochimp
 * @date 2024-03-12
 */
@RestController
@RequestMapping("/wx/social")
public class WxSocialController extends BaseController {

    @Autowired
    private WxSocialService wxSocialService;

    /**
     * 宠物种类
     */
    @PostMapping("/category")
    @ResponseBody
    @Operation(summary = "宠物种类", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult category(@RequestParam(value = "code", required = false) String code){
        return new GlobalResult();
    }

    /**
     * 宠物列表
     */
    @PostMapping("/petList")
    @ResponseBody
    @Operation(summary = "宠物列表", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult petList(@RequestBody UserInfo UserInfo){
        return new GlobalResult();
    }

    /**
     * 宠物详情
     */
    @PostMapping("/petDetail")
    @ResponseBody
    @Operation(summary = "宠物详情", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult petDetail(@RequestBody UserInfo UserInfo){
        return new GlobalResult();
    }
}








