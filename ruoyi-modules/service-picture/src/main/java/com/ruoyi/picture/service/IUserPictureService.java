package com.ruoyi.picture.service;

import java.util.List;
import com.ruoyi.picture.domain.UserPicture;

/**
 * 图文管理Service接口
 * 
 * @author ruoyi
 * @date 2023-12-04
 */
public interface IUserPictureService 
{
    /**
     * 查询图文管理
     * 
     * @param contentId 图文管理主键
     * @return 图文管理
     */
    public UserPicture selectUserPictureByContentId(Long contentId);

    /**
     * 查询图文管理列表
     * 
     * @param userPicture 图文管理
     * @return 图文管理集合
     */
    public List<UserPicture> selectUserPictureList(UserPicture userPicture);

    /**
     * 新增图文管理
     * 
     * @param userPicture 图文管理
     * @return 结果
     */
    public int insertUserPicture(UserPicture userPicture);

    /**
     * 修改图文管理
     * 
     * @param userPicture 图文管理
     * @return 结果
     */
    public int updateUserPicture(UserPicture userPicture);

    /**
     * 批量删除图文管理
     * 
     * @param contentIds 需要删除的图文管理主键集合
     * @return 结果
     */
    public int deleteUserPictureByContentIds(Long[] contentIds);

    /**
     * 删除图文管理信息
     * 
     * @param contentId 图文管理主键
     * @return 结果
     */
    public int deleteUserPictureByContentId(Long contentId);
}
