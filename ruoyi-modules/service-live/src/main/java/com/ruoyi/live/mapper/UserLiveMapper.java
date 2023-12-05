package com.ruoyi.live.mapper;

import java.util.List;
import com.ruoyi.live.domain.UserLive;

/**
 * 直播Mapper接口
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
public interface UserLiveMapper 
{
    /**
     * 查询直播
     * 
     * @param liveId 直播主键
     * @return 直播
     */
    public UserLive selectUserLiveByLiveId(Long liveId);

    /**
     * 查询直播列表
     * 
     * @param userLive 直播
     * @return 直播集合
     */
    public List<UserLive> selectUserLiveList(UserLive userLive);

    /**
     * 新增直播
     * 
     * @param userLive 直播
     * @return 结果
     */
    public int insertUserLive(UserLive userLive);

    /**
     * 修改直播
     * 
     * @param userLive 直播
     * @return 结果
     */
    public int updateUserLive(UserLive userLive);

    /**
     * 删除直播
     * 
     * @param liveId 直播主键
     * @return 结果
     */
    public int deleteUserLiveByLiveId(Long liveId);

    /**
     * 批量删除直播
     * 
     * @param liveIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserLiveByLiveIds(Long[] liveIds);
}
