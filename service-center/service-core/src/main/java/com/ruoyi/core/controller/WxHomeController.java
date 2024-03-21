package com.ruoyi.core.controller;

import com.ruoyi.common.core.domain.GlobalResult;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.core.constant.ContentTypeConstant;
import com.ruoyi.core.constant.MapperConstant;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.*;
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
    public TableDataInfo showContentInfo(@RequestParam Integer operationType, @RequestParam Integer pageNum,
                                         @RequestParam(value = "petId", required = false) String petId, //社区宠物id
                                         @RequestParam(value = "openId", required = false) String openId, //用户id
                                         @RequestParam(value = "contentType", required = false) String contentType) {
        List<ContentInfo> contentInfos=null;
        // 分类: ContentTypeConstant
        switch (operationType) {
            case ContentTypeConstant.recommendContent: //推荐（先乱序）
                contentInfos = wxHomeService.showAllContentInfo(pageNum,null);
                Collections.shuffle(contentInfos); break;
            case ContentTypeConstant.hotContent: // 热榜
                contentInfos = wxHomeService.showHotContentInfo(pageNum, " like_count desc"); break;
            case ContentTypeConstant.newContent: // 最新
                contentInfos = wxHomeService.showAllContentInfo(pageNum, MapperConstant.orderByUpdateTime); break;
            case ContentTypeConstant.petCategoryContent:  // 宠物社区
                if(petId!=null){ contentInfos=wxHomeService.petCategoryContentInfo(petId,pageNum,MapperConstant.orderByUpdateTime);
                }else return getDataTable(new ArrayList<>()); break;
            case ContentTypeConstant.followContent: // 关注
                if (openId != null) { contentInfos=wxHomeService.followContentInfo(openId,pageNum,MapperConstant.orderByUpdateTime);
                } else return getDataTable(new ArrayList<>()); break;
            case ContentTypeConstant.picContent: //图文
                contentInfos=wxHomeService.picContentInfo(pageNum,MapperConstant.orderByUpdateTime); break;
            case ContentTypeConstant.videoContent: //视频
                contentInfos=wxHomeService.videoContentInfo(pageNum,MapperConstant.orderByUpdateTime); break;
            case ContentTypeConstant.catContent: //猫咪
                contentInfos=wxHomeService.catContentInfo(pageNum,MapperConstant.orderByUpdateTime); break;
            case ContentTypeConstant.dogContent: //修狗
                contentInfos=wxHomeService.dogContentInfo(pageNum,MapperConstant.orderByUpdateTime); break;
            case ContentTypeConstant.userContent: //用户(0图文/1视频)
                if(openId!=null && contentType!=null) contentInfos=wxHomeService.userContentInfo(openId,contentType,pageNum,null);
                else return getDataTable(new ArrayList<>()); break;
        }
        return getDataTable(contentInfos);
    }

    /**
     * 查看content详情
     */
    @GetMapping("/showContentInfoById")
    public TableDataInfo showContentInfoById(@RequestParam String contentId) {
        startPage();
        return getDataTable(wxHomeService.showAllContentById(contentId));
    }

    /**
     * 查看user详细信息
     */
    @GetMapping("/showUserDetailInfo")
    public GlobalResult showUserDetailInfo(@RequestParam String userId) {
        GlobalResult globalResult = new GlobalResult();
        globalResult.setData(wxHomeService.showUserDetailInfo(userId));
        globalResult.setMsg("查询成功");
        globalResult.setStatus(200);
        return globalResult;
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

    /**
     * 通过UserId查询（关注/粉丝）列表
     */
    @GetMapping("/showUserInfoByUserId")
    @Operation(summary = "通过UserId查询（关注/粉丝）列表", security = {@SecurityRequirement(name = "Authorization")})
    public TableDataInfo showUserInfoByUserId(@RequestParam String listType,
                                              @RequestParam String userId){
        List<ContentUserInfo> contentUserInfos = wxHomeService.showUserInfoByUserId(listType, userId);
        return getDataTable(contentUserInfos);
    }

    /**
     * 查看content详情
     */
    @GetMapping("/showHotList")
    public TableDataInfo showHotList() {
        List<HotPetListInfo> hotPetListInfo = wxHomeService.showHotList();
        return getDataTable(hotPetListInfo);
    }

    /**
     * 添加评论
     */
    @PostMapping("/addUserComment")
    @ResponseBody
    @Operation(summary = "添加评论", security = {@SecurityRequirement(name = "Authorization")})
    public GlobalResult addUserComment(@RequestBody UserCommentRes userCommentRes){
        return GlobalResult.ok("affectRow:"+wxHomeService.addUserComment(userCommentRes));
    }
}








