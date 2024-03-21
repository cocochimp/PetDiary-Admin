package com.ruoyi.core.service;

import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.*;

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
    List<ContentInfo> showAllContentInfo(Integer pageNum,String OrderByInfo);
    List<ContentDetailInfo> showAllContentById(String contentId);
    List<ContentInfo> showHotContentInfo(Integer pageNum,String OrderByInfo);

    /**
     * 展示所有content的内容(分类)
     */
    List<ContentInfo> petCategoryContentInfo(String petId,Integer pageNum,String OrderByInfo);
    List<ContentInfo> followContentInfo(String openId,Integer pageNum,String OrderByInfo);
    List<ContentInfo> picContentInfo(Integer pageNum,String OrderByInfo);
    List<ContentInfo> videoContentInfo(Integer pageNum,String OrderByInfo);

    List<ContentInfo> catContentInfo(Integer pageNum,String OrderByInfo);
    List<ContentInfo> dogContentInfo(Integer pageNum,String OrderByInfo);
    List<ContentInfo> userContentInfo(String openId,String contentType,Integer pageNum,String OrderByInfo);

    /**
     * 通过type种类查询宠物名称
     */
    List<WxPetListInfo> showPetNameByPetType(int type);

    /**
     * 发布新内容
     */
    void insertContentInfo(UserContent userContent);
    /**
     * 添加内容
     */
    int addUserComment(UserCommentRes userCommentRes);

    /**
     * 通过id查询个人信息
     */
    UserDetailInfo showUserDetailInfo(String userId);

    /**
     * 通过UserId查询（关注/粉丝）列表
     */
    List<ContentUserInfo> showUserInfoByUserId(String listType,String userId);

    /**
     * 展示宠物热榜
     */
    List<HotPetListInfo> showHotList();
}
