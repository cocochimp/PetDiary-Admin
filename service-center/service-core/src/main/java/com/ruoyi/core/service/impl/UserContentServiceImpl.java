package com.ruoyi.core.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.core.domain.UserContent;
import com.ruoyi.core.enums.ContentType;
import com.ruoyi.core.mapper.UserContentMapper;
import com.ruoyi.core.service.IUserContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 图文管理Service业务层处理
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class UserContentServiceImpl implements IUserContentService
{
    @Autowired
    private UserContentMapper userContentMapper;

    /**
     * 查询图文管理
     * 
     * @param contentId 图文管理主键
     * @return 图文管理
     */
    @Override
    public UserContent selectUserPictureByContentId(Long contentId)
    {
        return userContentMapper.selectUserPictureByContentId(contentId);
    }

    /**
     * 查询图文管理列表
     * 
     * @param userContent 图文管理
     * @return 图文管理
     */
    @Override
    public List<UserContent> selectUserPictureList(UserContent userContent)
    {
        userContent.setContentType(String.valueOf(ContentType.PICTURE.getCode()));
        return userContentMapper.selectUserPictureList(userContent);
    }

    /**
     * 新增图文管理
     * 
     * @param userContent 图文管理
     * @return 结果
     */
    @Override
    public int insertUserPicture(UserContent userContent)
    {
        userContent.setContentType(String.valueOf(ContentType.PICTURE.getCode()));
        userContent.setCreateTime(DateUtils.getNowDate());
        return userContentMapper.insertUserPicture(userContent);
    }

    /**
     * 修改图文管理
     * 
     * @param userContent 图文管理
     * @return 结果
     */
    @Override
    public int updateUserPicture(UserContent userContent)
    {
        userContent.setUpdateTime(DateUtils.getNowDate());
        return userContentMapper.updateUserPicture(userContent);
    }

    /**
     * 批量删除图文管理
     * 
     * @param contentIds 需要删除的图文管理主键
     * @return 结果
     */
    @Override
    public int deleteUserPictureByContentIds(Long[] contentIds)
    {
        return userContentMapper.deleteUserPictureByContentIds(contentIds);
    }

    /**
     * 删除图文管理信息
     * 
     * @param contentId 图文管理主键
     * @return 结果
     */
    @Override
    public int deleteUserPictureByContentId(Long contentId)
    {
        return userContentMapper.deleteUserPictureByContentId(contentId);
    }
}
