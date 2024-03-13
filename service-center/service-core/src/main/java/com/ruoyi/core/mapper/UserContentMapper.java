package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserContent;

import java.util.List;

/**
 * 图文管理Mapper接口
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
public interface UserContentMapper
{


    /**
     * 查询图文管理
     * 
     * @param contentId 图文管理主键
     * @return 图文管理
     */
    public UserContent selectUserPictureByContentId(Long contentId);

    /**
     * 查询图文管理列表
     * 
     * @param userContent 图文管理
     * @return 图文管理集合
     */
    public List<UserContent> selectUserPictureList(UserContent userContent);

    /**
     * 新增图文管理
     * 
     * @param userContent 图文管理
     * @return 结果
     */
    public int insertUserPicture(UserContent userContent);

    /**
     * 修改图文管理
     * 
     * @param userContent 图文管理
     * @return 结果
     */
    public int updateUserPicture(UserContent userContent);

    /**
     * 删除图文管理
     * 
     * @param contentId 图文管理主键
     * @return 结果
     */
    public int deleteUserPictureByContentId(Long contentId);

    /**
     * 批量删除图文管理
     *
     * @param contentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserPictureByContentIds(Long[] contentIds);

}
