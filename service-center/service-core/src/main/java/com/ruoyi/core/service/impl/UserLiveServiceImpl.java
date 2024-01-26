package com.ruoyi.core.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.core.domain.UserLive;
import com.ruoyi.core.mapper.UserLiveMapper;
import com.ruoyi.core.service.IUserLiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 直播Service业务层处理
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class UserLiveServiceImpl implements IUserLiveService
{
    @Autowired
    private UserLiveMapper userLiveMapper;

    /**
     * 查询直播
     * 
     * @param liveId 直播主键
     * @return 直播
     */
    @Override
    public UserLive selectUserLiveByLiveId(Long liveId)
    {
        return userLiveMapper.selectUserLiveByLiveId(liveId);
    }

    /**
     * 查询直播列表
     * 
     * @param userLive 直播
     * @return 直播
     */
    @Override
    public List<UserLive> selectUserLiveList(UserLive userLive)
    {
        return userLiveMapper.selectUserLiveList(userLive);
    }

    /**
     * 新增直播
     * 
     * @param userLive 直播
     * @return 结果
     */
    @Override
    public int insertUserLive(UserLive userLive)
    {
        userLive.setCreateTime(DateUtils.getNowDate());
        return userLiveMapper.insertUserLive(userLive);
    }

    /**
     * 修改直播
     * 
     * @param userLive 直播
     * @return 结果
     */
    @Override
    public int updateUserLive(UserLive userLive)
    {
        userLive.setUpdateTime(DateUtils.getNowDate());
        return userLiveMapper.updateUserLive(userLive);
    }

    /**
     * 批量删除直播
     * 
     * @param liveIds 需要删除的直播主键
     * @return 结果
     */
    @Override
    public int deleteUserLiveByLiveIds(Long[] liveIds)
    {
        return userLiveMapper.deleteUserLiveByLiveIds(liveIds);
    }

    /**
     * 删除直播信息
     * 
     * @param liveId 直播主键
     * @return 结果
     */
    @Override
    public int deleteUserLiveByLiveId(Long liveId)
    {
        return userLiveMapper.deleteUserLiveByLiveId(liveId);
    }
}
