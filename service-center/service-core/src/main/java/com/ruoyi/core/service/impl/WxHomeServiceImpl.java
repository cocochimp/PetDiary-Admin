package com.ruoyi.core.service.impl;

import com.ruoyi.core.domain.vo.ContentInfo;
import com.ruoyi.core.domain.vo.WxPetListInfo;
import com.ruoyi.core.mapper.WxHomeMapper;
import com.ruoyi.core.service.WxHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<ContentInfo> showAllContentInfoOrderByUpdateTime() {
        showAllContentInfo();
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent();
        if(contentInfo!=null && contentInfo.size()>0){
            for(ContentInfo res:contentInfo){
                res.setUserInfo(wxHomeMapper.showUserInfo(res.getUserId()));
                res.setUserComment(wxHomeMapper.contentCommentInfo(res.getContentId()));
                res.setLikeCount(wxHomeMapper.contentLikeCount(res.getContentId()));
                res.setFanCount((wxHomeMapper.contentFansInfo(res.getUserId())).size());
            }
        }
        return contentInfo;
    }

    @Override
    public List<ContentInfo> showAllContentInfoOrderByLikeCount() {
        List<ContentInfo> contentInfo = wxHomeMapper.showAllContent();
        if(contentInfo!=null && contentInfo.size()>0){
            for(ContentInfo res:contentInfo){
                res.setUserInfo(wxHomeMapper.showUserInfo(res.getUserId()));
                res.setUserComment(wxHomeMapper.contentCommentInfo(res.getContentId()));
                res.setLikeCount(wxHomeMapper.contentLikeCount(res.getContentId()));
                res.setFanCount((wxHomeMapper.contentFansInfo(res.getUserId())).size());
            }
        }
        return contentInfo;
    }

    @Override
    public List<WxPetListInfo> showPetNameByPetType(int type) {
        List<WxPetListInfo> wxPetListInfo = wxHomeMapper.showPetNameByPetType(type);
        return wxPetListInfo;
    }

}
