package com.ruoyi.core.mapper;

import com.ruoyi.core.domain.UserComment;

import java.util.List;

/**
 * 评论管理Mapper接口
 * 
 * @author cocochimp
 * @date 2023-12-05
 */
public interface UserCommentMapper 
{
    /**
     * 查询评论管理
     * 
     * @param commentId 评论管理主键
     * @return 评论管理
     */
    public UserComment selectUserCommentByCommentId(Long commentId);

    /**
     * 查询评论管理列表
     * 
     * @param userComment 评论管理
     * @return 评论管理集合
     */
    public List<UserComment> selectUserCommentList(UserComment userComment);

    /**
     * 新增评论管理
     * 
     * @param userComment 评论管理
     * @return 结果
     */
    public int insertUserComment(UserComment userComment);

    /**
     * 修改评论管理
     * 
     * @param userComment 评论管理
     * @return 结果
     */
    public int updateUserComment(UserComment userComment);

    /**
     * 删除评论管理
     * 
     * @param commentId 评论管理主键
     * @return 结果
     */
    public int deleteUserCommentByCommentId(Long commentId);

    /**
     * 批量删除评论管理
     * 
     * @param commentIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteUserCommentByCommentIds(Long[] commentIds);
}
