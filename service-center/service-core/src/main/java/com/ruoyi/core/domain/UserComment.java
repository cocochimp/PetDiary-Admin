package com.ruoyi.core.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.annotation.Excel;
import com.ruoyi.common.core.web.domain.BaseEntity;

/**
 * 评论管理对象 user_content_comment
 * 
 * @author cocochimp
 * @date 2023-12-05w
 */
public class UserComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    private Long commentId;

    /** 用户ID */
    @Excel(name = "用户ID")
    private String userId;

    /** 内容ID */
    @Excel(name = "内容ID")
    private Long contentId;

    /** 评论内容 */
    @Excel(name = "评论内容")
    private String commentInfo;

    /** 上级评论ID */
    @Excel(name = "上级评论ID")
    private Long parentCommentId;

    /** 评论状态（0正常 1驳回) */
    @Excel(name = "评论状态", readConverterExp = "评论状态（0正常 1驳回)")
    private String status;

    /** 驳回内容 */
    private String rejectInfo;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setCommentId(Long commentId) 
    {
        this.commentId = commentId;
    }

    public Long getCommentId() 
    {
        return commentId;
    }
    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserId()
    {
        return userId;
    }
    public void setContentId(Long contentId) 
    {
        this.contentId = contentId;
    }

    public Long getContentId() 
    {
        return contentId;
    }
    public void setCommentInfo(String commentInfo) 
    {
        this.commentInfo = commentInfo;
    }

    public String getCommentInfo() 
    {
        return commentInfo;
    }
    public void setParentCommentId(Long parentCommentId) 
    {
        this.parentCommentId = parentCommentId;
    }

    public Long getParentCommentId() 
    {
        return parentCommentId;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setRejectInfo(String rejectInfo) 
    {
        this.rejectInfo = rejectInfo;
    }

    public String getRejectInfo() 
    {
        return rejectInfo;
    }
    public void setDelFlag(String delFlag) 
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag() 
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("commentId", getCommentId())
            .append("userId", getUserId())
            .append("contentId", getContentId())
            .append("commentInfo", getCommentInfo())
            .append("parentCommentId", getParentCommentId())
            .append("status", getStatus())
            .append("rejectInfo", getRejectInfo())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
