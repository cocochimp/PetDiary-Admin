package com.ruoyi.core.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCommentRes
{
    /** 用户ID */
    private String userId;

    /** 内容ID */
    private Long contentId;

    /** 评论内容 */
    private String commentInfo;

    /** 上级评论ID */
    private Long parentCommentId;
}
