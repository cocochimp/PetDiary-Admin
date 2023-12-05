package com.ruoyi.video.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.video.mapper.UserVideoMapper;
import com.ruoyi.video.domain.UserVideo;
import com.ruoyi.video.service.IUserVideoService;

/**
 * 视频管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
@Service
public class UserVideoServiceImpl implements IUserVideoService 
{
    @Autowired
    private UserVideoMapper userVideoMapper;

    /**
     * 查询视频管理
     * 
     * @param contentId 视频管理主键
     * @return 视频管理
     */
    @Override
    public UserVideo selectUserVideoByContentId(Long contentId)
    {
        return userVideoMapper.selectUserVideoByContentId(contentId);
    }

    /**
     * 查询视频管理列表
     * 
     * @param userVideo 视频管理
     * @return 视频管理
     */
    @Override
    public List<UserVideo> selectUserVideoList(UserVideo userVideo)
    {
        return userVideoMapper.selectUserVideoList(userVideo);
    }

    /**
     * 新增视频管理
     * 
     * @param userVideo 视频管理
     * @return 结果
     */
    @Override
    public int insertUserVideo(UserVideo userVideo)
    {
        userVideo.setCreateTime(DateUtils.getNowDate());
        return userVideoMapper.insertUserVideo(userVideo);
    }

    /**
     * 修改视频管理
     * 
     * @param userVideo 视频管理
     * @return 结果
     */
    @Override
    public int updateUserVideo(UserVideo userVideo)
    {
        userVideo.setUpdateTime(DateUtils.getNowDate());
        return userVideoMapper.updateUserVideo(userVideo);
    }

    /**
     * 批量删除视频管理
     * 
     * @param contentIds 需要删除的视频管理主键
     * @return 结果
     */
    @Override
    public int deleteUserVideoByContentIds(Long[] contentIds)
    {
        return userVideoMapper.deleteUserVideoByContentIds(contentIds);
    }

    /**
     * 删除视频管理信息
     * 
     * @param contentId 视频管理主键
     * @return 结果
     */
    @Override
    public int deleteUserVideoByContentId(Long contentId)
    {
        return userVideoMapper.deleteUserVideoByContentId(contentId);
    }
}
