package com.ruoyi.core.domain.vo;

import lombok.Data;

/**
 * 用户列表对象 user_info
 * 
 * @author cocochimp
 * @date 2023-12-04
 */
@Data
public class ContentCommentInfo
{
    private static final long serialVersionUID = 1L;

    /** 评论ID */
    private Long commentId;

    /** 用户ID */
    private String userId;

    /** 内容ID */
    private Long contentId;

    /** 评论内容 */
    private String commentInfo;

    /** 上级评论ID */
    private Long parentCommentId;
}
