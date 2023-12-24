package com.ruoyi.core.service.impl;

import com.ruoyi.common.core.enums.DelFlag;
import com.ruoyi.common.core.utils.DateUtils;
import com.ruoyi.core.domain.UserComment;
import com.ruoyi.core.mapper.UserCommentMapper;
import com.ruoyi.core.service.IUserCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

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
        List<UserComment> userComments = userCommentMapper.selectUserCommentList(userComment);
        userComments.removeIf(i -> Objects.equals(i.getDelFlag(), DelFlag.DELETE.getCode()));
        return userComments;
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
        UserComment userComment = new UserComment();
        userComment.setDelFlag(DelFlag.DELETE.getCode());
        int row = 0;
        for (Long commentId : commentIds) {
            userComment.setCommentId(commentId);
            row += userCommentMapper.updateUserComment(userComment);
        }
        return row;
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
        UserComment userComment = new UserComment();
        userComment.setCommentId(commentId);
        userComment.setDelFlag(DelFlag.DELETE.getCode());
        return userCommentMapper.updateUserComment(userComment);
    }
}
