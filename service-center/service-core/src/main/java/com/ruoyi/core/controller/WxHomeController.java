package com.ruoyi.core.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.core.constant.ContentTypeConstant;
import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.mapper.WxHomeMapper;
import com.ruoyi.core.service.WxHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @Autowired
    private WxHomeMapper wxHomeMapper;

    /**
     * 查看所有content内容
     */
    @GetMapping("/showContentInfo")
    public TableDataInfo showContentInfo(@RequestParam Integer operationType,
                                         @RequestParam(value = "petId", required = false) String petId, //社区宠物id
                                         @RequestParam(value = "openId", required = false) String openId) {
        startPage();
        List<ContentInfo> contentInfos = wxHomeService.showAllContentInfo();
        switch (operationType) {
            case ContentTypeConstant.newContent: // 最新内容
                contentInfos.sort((ci1, ci2) -> ci2.getUpdateTime().compareTo(ci1.getUpdateTime()));
                break;
            case ContentTypeConstant.hotContent: // 热榜内容
                contentInfos.sort((ci1, ci2) -> ci2.getLikeCount().compareTo(ci1.getLikeCount()));
                break;
            case ContentTypeConstant.followContent: // 关注内容
                if (openId != null) {
                    List<String> userAttentions = wxHomeMapper.contentAttentionInfo(openId); // 关注的列表id
                    contentInfos = contentInfos.stream()
                            .filter(contentInfo -> userAttentions.contains(contentInfo.getUserId()))
                            .collect(Collectors.toList());
                } else {
                    return getDataTable(new ArrayList<>());
                }
                break;
            case ContentTypeConstant.petCategoryContent:  // 社区内容
                if(petId==null) break;
                contentInfos = contentInfos.stream()
                        .filter(contentInfo -> petId.equals(contentInfo.getUserPet().getPetId()))
                        .collect(Collectors.toList());
                break;
            default:
                // Handle default case if needed
        }
        return getDataTable(contentInfos);
    }
}








