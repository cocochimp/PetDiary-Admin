package com.ruoyi.core.service;

import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.domain.vo.ContentUserInfo;
import com.ruoyi.core.domain.vo.UserDetailInfo;
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
    List<ContentInfo> userContentInfo(List<ContentInfo> contentInfos,String openId,String contentType);

    /**
     * 通过type种类查询宠物名称
     */
    List<WxPetListInfo> showPetNameByPetType(int type);

    /**
     * 发布新内容
     */
    void insertContentInfo(UserContent userContent);

    /**
     * 通过id查询个人信息
     */
    UserDetailInfo showUserDetailInfo(String userId);

    /**
     * 通过UserId查询（关注/粉丝）列表
     */
    List<ContentUserInfo> showUserInfoByUserId(String listType,String userId);
}
