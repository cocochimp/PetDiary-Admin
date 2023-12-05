package com.ruoyi.comment.service;

import java.util.List;
import com.ruoyi.comment.domain.UserComment;

/**
 * 评论管理Service接口
 * 
 * @author ruoyi
 * @date 2023-12-05
 */
public interface IUserCommentService 
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
     * 批量删除评论管理
     * 
     * @param commentIds 需要删除的评论管理主键集合
     * @return 结果
     */
    public int deleteUserCommentByCommentIds(Long[] commentIds);

    /**
     * 删除评论管理信息
     * 
     * @param commentId 评论管理主键
     * @return 结果
     */
    public int deleteUserCommentByCommentId(Long commentId);
}
