package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserInfo;

import java.util.List;

/**
 * 用户列表Mapper接口
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface UserInfoMapper 
{
    /**
     * 查询用户列表
     * 
     * @param userId 用户列表主键
     * @return 用户列表
     */
    public UserInfo selectUserInfoByUserId(String userId);

    /**
     * 查询用户列表列表
     * 
     * @param userInfo 用户列表
     * @return 用户列表集合
     */
    public List<UserInfo> selectUserInfoList(UserInfo userInfo);

    /**
     * 新增用户列表
     * 
     * @param userInfo 用户列表
     * @return 结果
     */
    public int insertUserInfo(UserInfo userInfo);

    /**
     * 修改用户列表
     * 
     * @param userInfo 用户列表
     * @return 结果
     */
    public int updateUserInfo(UserInfo userInfo);

    /**
     * 删除用户列表
     * 
     * @param userId 用户列表主键
     * @return 结果
     */
    public int deleteUserInfoByUserId(Long userId);

    /**
     * 批量删除用户列表
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserInfoByUserIds(Long[] userIds);

    void insertWechatUserInfo(UserInfo userInfo);

    void updateWechatUserInfo(UserInfo userInfo);

    void deleteWechatUserInfo(String openId);

    UserInfo getWechatUserInfoByOpenId(String openId);

    List<UserInfo> getAllWechatUserInfo();
}
