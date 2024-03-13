package com.ruoyi.core.service.impl;

import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.core.domain.UserInfo;
import com.ruoyi.core.enums.DelFlag;
import com.ruoyi.core.mapper.WxUserMapper;
import com.ruoyi.core.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 用户列表Service业务层处理
 *
 * @author cocochimp
 * @date 2023-12-04
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService
{
    @Autowired
    private WxUserMapper wxUserMapper;

    /**
     * 查询用户列表
     *
     * @param userId 用户列表主键
     * @return 用户列表
     */
    @Override
    public UserInfo selectUserInfoByUserId(String userId)
    {
        return wxUserMapper.selectUserInfoByUserId(userId);
    }

    /**
     * 查询用户列表列表
     *
     * @param userInfo 用户列表
     * @return 用户列表
     */
    @Override
    public List<UserInfo> selectUserInfoList(UserInfo userInfo)
    {
        List<UserInfo> userInfos = wxUserMapper.selectUserInfoList(userInfo);
        userInfos.removeIf(i -> Objects.equals(i.getDelFlag(), DelFlag.DELETE.getCode()));
        return userInfos;
    }

    /**
     * 新增用户列表
     *
     * @param userInfo 用户列表
     * @return 结果
     */
    @Override
    public int insertUserInfo(UserInfo userInfo)
    {
        userInfo.setCreateTime(DateUtils.getNowDate());
        return wxUserMapper.insertUserInfo(userInfo);
    }

    /**
     * 修改用户列表
     *
     * @param userInfo 用户列表
     * @return 结果
     */
    @Override
    public int updateUserInfo(UserInfo userInfo)
    {
        userInfo.setUpdateTime(DateUtils.getNowDate());
        return wxUserMapper.updateUserInfo(userInfo);
    }

    /**
     * 批量删除用户列表
     *
     * @param userIds 需要删除的用户列表主键
     * @return 结果
     */
    @Override
    public int deleteUserInfoByUserIds(String[] userIds)
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setDelFlag(DelFlag.DELETE.getCode());
        int row = 0;
        for (String userId : userIds) {
            userInfo.setOpenid(userId);
            row += wxUserMapper.updateUserInfo(userInfo);
        }
        return row;
    }

    /**
     * 删除用户列表信息
     *
     * @param userId 用户列表主键
     * @return 结果
     */
    @Override
    public int deleteUserInfoByUserId(Long userId)
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setOpenid(String.valueOf(userId));
        userInfo.setDelFlag(DelFlag.DELETE.getCode());
        return wxUserMapper.updateUserInfo(userInfo);
    }

}
