package com.ruoyi.core.service.impl;

import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;
import com.ruoyi.core.enums.ContentStatus;
import com.ruoyi.core.mapper.WxHomeMapper;
import com.ruoyi.core.service.WxHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    @Autowired
    private WxHomeMapper wxHomeMapper;

    @Override
    public List<ContentInfo> showAllContentInfo() {
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent();
        if(contentInfo!=null && contentInfo.size()>0){
            for(ContentInfo res:contentInfo){
                res.setUserInfo(wxHomeMapper.showUserInfo(res.getUserId()));
                res.setUserComment(wxHomeMapper.contentCommentInfo(res.getContentId()));
                res.setLikeCount(wxHomeMapper.contentLikeCount(res.getContentId()));
                res.setFanCount((wxHomeMapper.contentFansInfo(res.getUserId())).size());

                int petId = wxHomeMapper.showPetIdByContentId(res.getContentId());
                res.setUserPet(wxHomeMapper.showPetDetailByPetId(petId));
            }
        }
        return contentInfo;
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
    public List<ContentInfo> petCategoryContentInfo(List<ContentInfo> contentInfos, String petId) {
        return contentInfos.stream()
                .filter(contentInfo -> petId.equals(contentInfo.getUserPet().getPetId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentInfo> followContentInfo(List<ContentInfo> contentInfos, String openId) {
        List<String> userAttentions = wxHomeMapper.contentAttentionInfo(openId); // 关注的列表id
        return contentInfos.stream()
                .filter(contentInfo -> userAttentions.contains(contentInfo.getUserId()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentInfo> picContentInfo(List<ContentInfo> contentInfos) {
        return contentInfos.stream()
                .filter(contentInfo -> "0".equals(contentInfo.getContentType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentInfo> videoContentInfo(List<ContentInfo> contentInfos) {
        return contentInfos.stream()
                .filter(contentInfo -> "1".equals(contentInfo.getContentType()))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentInfo> catContentInfo(List<ContentInfo> contentInfos) {
        return contentInfos.stream()
                .filter(contentInfo -> {
                    WxPetListInfo wxPetListInfo = wxHomeMapper.showPetDetailByPetId(Integer.parseInt(contentInfo.getUserPet().getPetId()));
                    return wxPetListInfo.getType()==0;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<ContentInfo> dogContentInfo(List<ContentInfo> contentInfos) {
        return contentInfos.stream()
                .filter(contentInfo -> {
                    WxPetListInfo wxPetListInfo = wxHomeMapper.showPetDetailByPetId(Integer.parseInt(contentInfo.getUserPet().getPetId()));
                    return wxPetListInfo.getType()==1;
                })
                .collect(Collectors.toList());
    }
}
