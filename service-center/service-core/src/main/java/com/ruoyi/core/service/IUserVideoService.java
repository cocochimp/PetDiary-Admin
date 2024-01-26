package com.ruoyi.core.service;

import com.ruoyi.core.domain.UserVideo;

import java.util.List;

/**
 * 视频管理Service接口
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface IUserVideoService 
{
    /**
     * 查询视频管理
     * 
     * @param contentId 视频管理主键
     * @return 视频管理
     */
    public UserVideo selectUserVideoByContentId(Long contentId);

    /**
     * 查询视频管理列表
     * 
     * @param userVideo 视频管理
     * @return 视频管理集合
     */
    public List<UserVideo> selectUserVideoList(UserVideo userVideo);

    /**
     * 新增视频管理
     * 
     * @param userVideo 视频管理
     * @return 结果
     */
    public int insertUserVideo(UserVideo userVideo);

    /**
     * 修改视频管理
     * 
     * @param userVideo 视频管理
     * @return 结果
     */
    public int updateUserVideo(UserVideo userVideo);

    /**
     * 批量删除视频管理
     * 
     * @param contentIds 需要删除的视频管理主键集合
     * @return 结果
     */
    public int deleteUserVideoByContentIds(Long[] contentIds);

    /**
     * 删除视频管理信息
     * 
     * @param contentId 视频管理主键
     * @return 结果
     */
    public int deleteUserVideoByContentId(Long contentId);
}
