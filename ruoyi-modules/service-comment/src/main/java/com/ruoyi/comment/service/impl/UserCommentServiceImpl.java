package com.ruoyi.comment.service.impl;

import java.util.List;
import com.ruoyi.common.core.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.comment.mapper.UserCommentMapper;
import com.ruoyi.comment.domain.UserComment;
import com.ruoyi.comment.service.IUserCommentService;

/**
 * 评论管理Service业务层处理
 * 
 * @author ruoyi
 * @date 2023-12-05
 */
@Service
public class UserCommentServiceImpl implements IUserCommentService 
{
    @Autowired
    private UserCommentMapper userCommentMapper;

    /**
     * 查询评论管理
     * 
     * @param commentId 评论管理主键
     * @return 评论管理
     */
    @Override
    public UserComment selectUserCommentByCommentId(Long commentId)
    {
        return userCommentMapper.selectUserCommentByCommentId(commentId);
    }

    /**
     * 查询评论管理列表
     * 
     * @param userComment 评论管理
     * @return 评论管理
     */
    @Override
    public List<UserComment> selectUserCommentList(UserComment userComment)
    {
        return userCommentMapper.selectUserCommentList(userComment);
    }

    /**
     * 新增评论管理
     * 
     * @param userComment 评论管理
     * @return 结果
     */
    @Override
    public int insertUserComment(UserComment userComment)
    {
        userComment.setCreateTime(DateUtils.getNowDate());
        return userCommentMapper.insertUserComment(userComment);
    }

    /**
     * 修改评论管理
     * 
     * @param userComment 评论管理
     * @return 结果
     */
    @Override
    public int updateUserComment(UserComment userComment)
    {
        userComment.setUpdateTime(DateUtils.getNowDate());
        return userCommentMapper.updateUserComment(userComment);
    }

    /**
     * 批量删除评论管理
     * 
     * @param commentIds 需要删除的评论管理主键
     * @return 结果
     */
    @Override
    public int deleteUserCommentByCommentIds(Long[] commentIds)
    {
        return userCommentMapper.deleteUserCommentByCommentIds(commentIds);
    }

    /**
     * 删除评论管理信息
     * 
     * @param commentId 评论管理主键
     * @return 结果
     */
    @Override
    public int deleteUserCommentByCommentId(Long commentId)
    {
        return userCommentMapper.deleteUserCommentByCommentId(commentId);
    }
}
