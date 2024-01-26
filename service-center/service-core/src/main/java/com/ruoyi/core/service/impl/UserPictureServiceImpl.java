package com.ruoyi.core.service.impl;

import com.ruoyi.core.enums.ContentType;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.core.domain.UserPicture;
import com.ruoyi.core.mapper.UserPictureMapper;
import com.ruoyi.core.service.IUserPictureService;
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
public class UserPictureServiceImpl implements IUserPictureService
{
    @Autowired
    private UserPictureMapper userPictureMapper;

    /**
     * 查询图文管理
     * 
     * @param contentId 图文管理主键
     * @return 图文管理
     */
    @Override
    public UserPicture selectUserPictureByContentId(Long contentId)
    {
        return userPictureMapper.selectUserPictureByContentId(contentId);
    }

    /**
     * 查询图文管理列表
     * 
     * @param userPicture 图文管理
     * @return 图文管理
     */
    @Override
    public List<UserPicture> selectUserPictureList(UserPicture userPicture)
    {
        userPicture.setContentType(String.valueOf(ContentType.PICTURE.getCode()));
        return userPictureMapper.selectUserPictureList(userPicture);
    }

    /**
     * 新增图文管理
     * 
     * @param userPicture 图文管理
     * @return 结果
     */
    @Override
    public int insertUserPicture(UserPicture userPicture)
    {
        userPicture.setContentType(String.valueOf(ContentType.PICTURE.getCode()));
        userPicture.setCreateTime(DateUtils.getNowDate());
        return userPictureMapper.insertUserPicture(userPicture);
    }

    /**
     * 修改图文管理
     * 
     * @param userPicture 图文管理
     * @return 结果
     */
    @Override
    public int updateUserPicture(UserPicture userPicture)
    {
        userPicture.setUpdateTime(DateUtils.getNowDate());
        return userPictureMapper.updateUserPicture(userPicture);
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
        return userPictureMapper.deleteUserPictureByContentIds(contentIds);
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
        return userPictureMapper.deleteUserPictureByContentId(contentId);
    }
}
