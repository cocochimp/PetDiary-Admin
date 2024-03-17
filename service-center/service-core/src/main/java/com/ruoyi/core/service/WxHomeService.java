package com.ruoyi.core.service;

import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;

import java.util.List;

/**
 * 用户列表
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface WxHomeService
{

    /**
     * 展示所有content的内容
     */
    List<ContentInfo> showAllContentInfo();
    List<ContentInfo> showAllContentById(String contentId);

    /**
     * 展示所有content的内容(分类)
     */
    List<ContentInfo> petCategoryContentInfo(List<ContentInfo> contentInfos,String petId);
    List<ContentInfo> followContentInfo(List<ContentInfo> contentInfos,String openId);
    List<ContentInfo> picContentInfo(List<ContentInfo> contentInfos);
    List<ContentInfo> videoContentInfo(List<ContentInfo> contentInfos);
    List<ContentInfo> catContentInfo(List<ContentInfo> contentInfos);
    List<ContentInfo> dogContentInfo(List<ContentInfo> contentInfos);
    List<ContentInfo> userContentInfo(List<ContentInfo> contentInfos,String openId);

    /**
     * 通过type种类查询宠物名称
     */
    List<WxPetListInfo> showPetNameByPetType(int type);

    /**
     * 发布新内容
     */
    void insertContentInfo(UserContent userContent);
}
