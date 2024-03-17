package com.ruoyi.core.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.core.constant.ContentTypeConstant;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;
import com.ruoyi.core.service.WxHomeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 小程序——用户
 *
 * @author cocochimp
 * @date 2024-03-12
 */
@RestController
@RequestMapping("/wx/home")
public class WxHomeController extends BaseController {

    @Autowired
    private WxHomeService wxHomeService;

    /**
     * 查看所有content内容
     */
    @GetMapping("/showContentInfo")
    public TableDataInfo showContentInfo(@RequestParam Integer operationType,
                                         @RequestParam(value = "petId", required = false) String petId, //社区宠物id
                                         @RequestParam(value = "openId", required = false) String openId) {
        startPage();
        List<ContentInfo> contentInfos = wxHomeService.showAllContentInfo();
        // 分类: ContentTypeConstant
        switch (operationType) {
            case ContentTypeConstant.recommendContent: //推荐（先乱序）
                Collections.shuffle(contentInfos); break;
            case ContentTypeConstant.hotContent: // 热榜
                contentInfos.sort((ci1, ci2) -> ci2.getLikeCount().compareTo(ci1.getLikeCount())); break;
            case ContentTypeConstant.newContent: // 最新
                contentInfos.sort((ci1, ci2) -> ci2.getUpdateTime().compareTo(ci1.getUpdateTime())); break;
            case ContentTypeConstant.petCategoryContent:  // 宠物社区
                if(petId!=null){ contentInfos=wxHomeService.petCategoryContentInfo(contentInfos,petId);
                }else return getDataTable(new ArrayList<>()); break;
            case ContentTypeConstant.followContent: // 关注
                if (openId != null) { contentInfos=wxHomeService.followContentInfo(contentInfos,openId);
                } else return getDataTable(new ArrayList<>()); break;
            case ContentTypeConstant.picContent: //图文
                contentInfos=wxHomeService.picContentInfo(contentInfos); break;
            case ContentTypeConstant.videoContent: //视频
                contentInfos=wxHomeService.videoContentInfo(contentInfos); break;
            case ContentTypeConstant.catContent: //猫咪
                contentInfos=wxHomeService.catContentInfo(contentInfos); break;
            case ContentTypeConstant.dogContent: //修狗
                contentInfos=wxHomeService.dogContentInfo(contentInfos); break;
            case ContentTypeConstant.userContent: //用户
                if(openId!=null) contentInfos=wxHomeService.userContentInfo(contentInfos,openId);
                else return getDataTable(new ArrayList<>()); break;
        }
        return getDataTable(contentInfos);
    }

    /**
     * 查看所有content内容
     */
    @GetMapping("/showContentInfoById")
    public TableDataInfo showContentInfoById(@RequestParam String contentId) {
        startPage();
        return getDataTable(wxHomeService.showAllContentById(contentId));
    }

    /**
     * 发布：通过type种类查询宠物名称
     */
    @PostMapping("/showPetNameByPetType")
    @ResponseBody
    @Operation(summary = "通过type种类查询宠物名称", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo showPetNameByPetType(@RequestParam int type){
        List<WxPetListInfo> wxPetListInfo = wxHomeService.showPetNameByPetType(type);
        return getDataTable(wxPetListInfo);
    }

    /**
     * 发布新内容
     */
    @PostMapping("/insertContentInfo")
    @ResponseBody
    @Operation(summary = "发布新内容", security = {@SecurityRequirement(name = "Authorization")})
    public AjaxResult insertContentInfo(@RequestBody UserContent userContent){
        wxHomeService.insertContentInfo(userContent);
        return success();
    }
}








