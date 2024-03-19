package com.ruoyi.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.ruoyi.core.constant.ContentTypeConstant;
import com.ruoyi.core.constant.ListTypeConstant;
import com.ruoyi.core.constant.MapperConstant;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.*;
import com.ruoyi.core.enums.ContentStatus;
import com.ruoyi.core.enums.PetType;
import com.ruoyi.core.mapper.WxHomeMapper;
import com.ruoyi.core.service.WxHomeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 宠物类别Service业务层处理
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class WxHomeServiceImpl implements WxHomeService
{

    private static final Logger logger = LoggerFactory.getLogger(WxHomeServiceImpl.class);

    @Autowired
    private WxHomeMapper wxHomeMapper;

    @Override
    public List<ContentInfo> showAllContentInfo(Integer pageNum,String OrderByInfo) {
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(null,null);
        return getContentInfos(contentInfo);
    }

    /*content详情*/
    @Override
    public List<ContentDetailInfo> showAllContentById(String contentId) {
        ContentDetailInfo contentInfo = wxHomeMapper.showAllContentById(contentId);
        if(contentInfo!=null){
            contentInfo.setUserInfo(wxHomeMapper.showUserInfo(contentInfo.getUserId()));
            contentInfo.setUserComment(wxHomeMapper.contentCommentInfo(contentInfo.getContentId()));
            contentInfo.setLikeCount(wxHomeMapper.contentLikeCount(contentInfo.getContentId()));
            contentInfo.setFanCount((wxHomeMapper.contentFansInfo(contentInfo.getUserId())).size());

            int petId = wxHomeMapper.showPetIdByContentId(contentInfo.getContentId());
            contentInfo.setUserPet(wxHomeMapper.showPetDetailByPetId(petId));
        }
        return Collections.singletonList(contentInfo);
    }

    @Override
    public List<ContentInfo> showHotContentInfo(Integer pageNum, String OrderByInfo) {
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfos = wxHomeMapper.showHotContent();
        return getContentInfos(contentInfos);
    }

    @Override
    public List<ContentInfo> petCategoryContentInfo(String petId,Integer pageNum,String OrderByInfo) {
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(ContentTypeConstant.petCategoryContent,petId);
        return getContentInfos(contentInfo);
    }

    @Override
    public List<ContentInfo> followContentInfo(String openId,Integer pageNum,String OrderByInfo) {
        List<String> userAttentions = wxHomeMapper.contentAttentionInfo(openId); // 关注的列表id
        String userIdList;
        if(userAttentions.size()==0) userIdList=null;
            //封装入参数据
        else userIdList = userAttentions.stream()
                .map(s -> "'" + s + "'").collect(Collectors.joining(","));
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(ContentTypeConstant.followContent,userIdList);
        return getContentInfos(contentInfo);
    }

    @Override
    public List<ContentInfo> picContentInfo(Integer pageNum,String OrderByInfo) {
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(ContentTypeConstant.picContent,null);
        return getContentInfos(contentInfo);
    }

    @Override
    public List<ContentInfo> videoContentInfo(Integer pageNum,String OrderByInfo) {
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(ContentTypeConstant.videoContent,null);
        return getContentInfos(contentInfo);
    }

    @Override
    public List<ContentInfo> catContentInfo(Integer pageNum,String OrderByInfo) {
        List<WxPetListInfo> wxPetListInfos = wxHomeMapper.showPetNameByPetType(Integer.parseInt(PetType.CAT.getCode()));
        String userIdList;
        if(wxPetListInfos.size()==0) userIdList=null;
            //封装入参数据
        else userIdList = wxPetListInfos.stream()
                .map(WxPetListInfo::getPetId)
                .collect(Collectors.joining(","));
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(ContentTypeConstant.catContent,userIdList);
        return getContentInfos(contentInfo);
    }

    @Override
    public List<ContentInfo> dogContentInfo(Integer pageNum,String OrderByInfo) {
        List<WxPetListInfo> wxPetListInfos = wxHomeMapper.showPetNameByPetType(Integer.parseInt(PetType.DOG.getCode()));
        String userIdList;
        if(wxPetListInfos.size()==0) userIdList=null;
            //封装入参数据
        else userIdList = wxPetListInfos.stream()
                .map(WxPetListInfo::getPetId)
                .collect(Collectors.joining(","));
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(ContentTypeConstant.dogContent,userIdList);
        return getContentInfos(contentInfo);
    }

    @Override
    public List<ContentInfo> userContentInfo(String openId,String contentType,Integer pageNum,String OrderByInfo) {
        PageHelper.startPage(pageNum, MapperConstant.pageSize,OrderByInfo);
        String res="'"+openId+"',"+contentType;
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent(ContentTypeConstant.userContent,res);
        return getContentInfos(contentInfo);
    }

    @Override
    public List<WxPetListInfo> showPetNameByPetType(int type) {
        List<WxPetListInfo> wxPetListInfo = wxHomeMapper.showPetNameByPetType(type);
        return wxPetListInfo;
    }

    @Override
    public void insertContentInfo(UserContent userContent) {
        userContent.setStatus(ContentStatus.EXAMINE.getCode());
        userContent.setRejectInfo(null);
        wxHomeMapper.insertContentInfo(userContent);
    }

    @Override
    public UserDetailInfo showUserDetailInfo(String userId) {
        UserDetailInfo userDetailInfo = new UserDetailInfo();
        userDetailInfo.setUserId(userId);
        userDetailInfo.setUserInfo(wxHomeMapper.showUserInfo(userId));
        userDetailInfo.setLikeCount(wxHomeMapper.contentLikeCountByUserId(userId));
        userDetailInfo.setFanCount(wxHomeMapper.contentFansInfo(userId).size());
        userDetailInfo.setAttentionCount(wxHomeMapper.contentAttentionInfo(userId).size());
        return userDetailInfo;
    }

    @Override
    public List<ContentUserInfo> showUserInfoByUserId(String listType, String userId) {
        List<ContentUserInfo> res = new ArrayList<>();
        if (ListTypeConstant.attentionList.equals(listType)) {
            List<String> attentionInfos = wxHomeMapper.contentAttentionInfo(userId);
            attentionInfos.stream()
                    .map(wxHomeMapper::showUserInfoByUserId)
                    .filter(Objects::nonNull)
                    .forEach(res::add);
        } else if (ListTypeConstant.fanList.equals(listType)) {
            List<String> userAttentions = wxHomeMapper.contentFansInfo(userId);
            userAttentions.stream()
                    .map(wxHomeMapper::showUserInfoByUserId)
                    .filter(Objects::nonNull)
                    .forEach(res::add);
        }
        return res;
    }

    /*封装content返回信息*/
    private List<ContentInfo> getContentInfos(List<ContentInfo> contentInfo) {
        if(contentInfo!=null && contentInfo.size()>0){
            for(ContentInfo res:contentInfo){
                res.setUserInfo(wxHomeMapper.showUserInfo(res.getUserId()));
                res.setLikeCount(wxHomeMapper.contentLikeCount(res.getContentId()));

                int petId = wxHomeMapper.showPetIdByContentId(res.getContentId());
                res.setUserPet(wxHomeMapper.showPetDetailByPetId(petId));
            }
        }
        return contentInfo;
    }
}
