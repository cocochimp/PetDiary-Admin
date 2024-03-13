package com.ruoyi.core.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.core.domain.UserPet;
import com.ruoyi.core.domain.vo.WxNationInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;
import com.ruoyi.core.service.WxSocialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * 通过“宠物类别”展示所有宠物数量
     */
    @PostMapping("/queryNationByType")
    @ResponseBody
    @Operation(summary = "通过“宠物类别”展示所有宠物数量", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo queryNationByType(@RequestParam(value = "type") int type){
        List<WxNationInfo> wxNationInfos = wxSocialService.showNationNumByType(type);
        return getDataTable(wxNationInfos);
    }

    /**
     * 通过“国籍“展示列表
     */
    @PostMapping("/queryListByNation")
    @ResponseBody
    @Operation(summary = "通过“国籍“展示列表", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo queryListByNation(@RequestParam(value = "type") int type,
                                           @RequestParam(value = "nation") String nation){
        List<WxPetListInfo> wxPetListInfos = wxSocialService.showListByNation(type, nation);
        return getDataTable(wxPetListInfos);
    }

    /**
     * 通过“宠物id”展示详情信息
     */
    @PostMapping("/queryPetDetailByPetId")
    @ResponseBody
    @Operation(summary = "通过“宠物id”展示详情信息", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo queryPetDetailByPetId(@RequestParam(value = "petId") int petId){
        List<UserPet> userPets = wxSocialService.showPetDetailByPetId(petId);
        return getDataTable(userPets);
    }
}








